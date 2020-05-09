#include "factroytest.h"
#include <sys/select.h>
#include <linux/rtc.h>
#include <jni.h>
#include <net/if.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <linux/can.h>
#include <linux/can/raw.h>

static char            dev_name[16];
/*******************************GPIO接口 START**********************************************************/
int Java_com_android_factorytest_TestPin_SetPin(JNIEnv* env,jobject thiz,jint cmd,jint arg)
{
	int dev;
	int ret;

		sprintf(dev_name,"/dev/wifi_power");
		LOGE("Java_com_android_factorytest_TestPin_SetPin,0x%x",cmd);
		dev = open(dev_name,O_WRONLY);
		if(dev == -1)
			LOGE("Cannot open '%s': %d, %s", dev_name, errno, strerror (errno));
		ret = ioctl(dev,cmd,arg);
		if(ret !=0)
			LOGE("ioctl failed : %d, %s",  errno, strerror (errno));
		close(dev);



	return ret;
}
int Java_com_android_factorytest_TestPin_GetPin(JNIEnv* env,jobject thiz,jint cmd,jint arg)
{
	int dev;
	int ret;
	int val;
	sprintf(dev_name,"/dev/wifi_power");
	LOGE("Java_com_android_factorytest_TestPin_GetPin,0x%x",cmd);
	dev = open(dev_name,O_WRONLY);
	if(dev == -1)
		LOGE("Cannot open '%s': %d, %s", dev_name, errno, strerror (errno));
	ret = ioctl(dev,cmd,&val);
	arg = val;
	if(ret !=0)
		LOGE("ioctl failed : %d, %s",  errno, strerror (errno));
	close(dev);
	return val;
}
int Java_com_android_factorytest_TestPin_ReadRtc(JNIEnv* env,jobject thiz,jint cmd,jint argp)
//int Java_com_android_factorytest_TestPin_ReadRtc(JNIEnv* env,jobject thiz,jint cmd,struct jrtc_time *tm)
{
	int dev;
	int ret;
	struct rtc_time tm;
	sprintf(dev_name,"/dev/wifi_power");
	LOGE("Java_com_android_factorytest_TestPin_GetPin,0x%x",cmd);
	dev = open(dev_name,O_WRONLY);
	if(dev == -1)
		LOGE("Cannot open '%s': %d, %s", dev_name, errno, strerror (errno));
	ret = ioctl(dev,cmd,&tm);

	//arg = val;
	if(ret !=0)
		LOGE("ioctl failed : %d, %s",  errno, strerror (errno));
	/*year = tm.tm_year+1900;
	*mon = tm.tm_mon+1;
	*day = tm.tm_mday;
	*hour = tm.tm_hour;
	*min = tm.tm_min;
	*sec = tm.tm_sec;*/
	LOGE("clock is %04d-%02d-%02d %02d:%02d:%02d\n",tm.tm_year+1900, tm.tm_mon+1, tm.tm_mday,tm.tm_hour+8, tm.tm_min, tm.  tm_sec);
	close(dev);
	return 0;
}

/****************************************GPIO接口 END**********************************************************/
/****************************************采集仪接口START*********************************************************************/
int errnoexit(const char *s)
{
	LOGE("%s error %d, %s", s, errno, strerror (errno));
	return ERROR_LOCAL;
}


int xioctl(int fd, int request, void *arg)
{
	int r;

	do r = ioctl (fd, request, arg);
	while (-1 == r && EINTR == errno);

	return r;
}
int checkCamerabase(void){
	struct stat st;
	int i;
	int start_from_4 = 1;

	/* if /dev/video[0-3] exist, camerabase=4, otherwise, camrerabase = 0 */
	for(i=0 ; i<4 ; i++){
		sprintf(dev_name,"/dev/video%d",i);
		if (-1 == stat (dev_name, &st)) {
			start_from_4 &= 0;
		}else{
			start_from_4 &= 1;
		}
	}

	if(start_from_4){
		return 4;
	}else{
		return 0;
	}
}

int opendevice(int i)
{
	struct stat st;

	sprintf(dev_name,"/dev/video%d",i);

	if (-1 == stat (dev_name, &st)) {
		LOGE("Cannot identify '%s': %d, %s", dev_name, errno, strerror (errno));
		return ERROR_LOCAL;
	}

	if (!S_ISCHR (st.st_mode)) {
		LOGE("%s is no device", dev_name);
		return ERROR_LOCAL;
	}

	fd = open (dev_name, O_RDWR , 0);

	if (-1 == fd) {
		LOGE("Cannot open '%s': %d, %s", dev_name, errno, strerror (errno));
		return ERROR_LOCAL;
	}
	return SUCCESS_LOCAL;
}

int initdevice(void)
{
	struct v4l2_capability cap;
	struct v4l2_cropcap cropcap;
	struct v4l2_crop crop;
	struct v4l2_format fmt;
	unsigned int min;

	if (-1 == xioctl (fd, VIDIOC_QUERYCAP, &cap)) {
		if (EINVAL == errno) {
			LOGE("%s is no V4L2 device", dev_name);
			return ERROR_LOCAL;
		} else {
			return errnoexit ("VIDIOC_QUERYCAP");
		}
	}

	if (!(cap.capabilities & V4L2_CAP_VIDEO_CAPTURE)) {
		LOGE("%s is no video capture device", dev_name);
		return ERROR_LOCAL;
	}

	if (!(cap.capabilities & V4L2_CAP_STREAMING)) {
		LOGE("%s does not support streaming i/o", dev_name);
		return ERROR_LOCAL;
	}

	CLEAR (cropcap);

	cropcap.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	if (0 == xioctl (fd, VIDIOC_CROPCAP, &cropcap)) {
		crop.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		crop.c = cropcap.defrect;

		if (-1 == xioctl (fd, VIDIOC_S_CROP, &crop)) {
			switch (errno) {
				case EINVAL:
					break;
				default:
					break;
			}
		}
	} else {
	}

	CLEAR (fmt);

	fmt.type                = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	fmt.fmt.pix.width       = IMG_WIDTH;
	fmt.fmt.pix.height      = IMG_HEIGHT;

	fmt.fmt.pix.pixelformat = V4L2_PIX_FMT_YUYV;
	fmt.fmt.pix.field       = V4L2_FIELD_INTERLACED;

	if (-1 == xioctl (fd, VIDIOC_S_FMT, &fmt))
		return errnoexit ("VIDIOC_S_FMT");

	min = fmt.fmt.pix.width * 2;
	if (fmt.fmt.pix.bytesperline < min)
		fmt.fmt.pix.bytesperline = min;
	min = fmt.fmt.pix.bytesperline * fmt.fmt.pix.height;
	if (fmt.fmt.pix.sizeimage < min)
		fmt.fmt.pix.sizeimage = min;

	return initmmap ();

}

int initmmap(void)
{
	struct v4l2_requestbuffers req;

	CLEAR (req);

	req.count               = 4;
	req.type                = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	req.memory              = V4L2_MEMORY_MMAP;

	if (-1 == xioctl (fd, VIDIOC_REQBUFS, &req)) {
		if (EINVAL == errno) {
			LOGE("%s does not support memory mapping", dev_name);
			return ERROR_LOCAL;
		} else {
			return errnoexit ("VIDIOC_REQBUFS");
		}
	}

	if (req.count < 2) {
		LOGE("Insufficient buffer memory on %s", dev_name);
		return ERROR_LOCAL;
 	}

	buffers = calloc (req.count, sizeof (*buffers));

	if (!buffers) {
		LOGE("Out of memory");
		return ERROR_LOCAL;
	}

	for (n_buffers = 0; n_buffers < req.count; ++n_buffers) {
		struct v4l2_buffer buf;

		 CLEAR (buf);

		buf.type        = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		buf.memory      = V4L2_MEMORY_MMAP;
		buf.index       = n_buffers;

		if (-1 == xioctl (fd, VIDIOC_QUERYBUF, &buf))
			return errnoexit ("VIDIOC_QUERYBUF");

		buffers[n_buffers].length = buf.length;
		buffers[n_buffers].start =
		mmap (NULL ,
			buf.length,
			PROT_READ | PROT_WRITE,
			MAP_SHARED,
			fd, buf.m.offset);

		if (MAP_FAILED == buffers[n_buffers].start)
			return errnoexit ("mmap");
	}

	return SUCCESS_LOCAL;
}

int startcapturing(void)
{
	unsigned int i;
	enum v4l2_buf_type type;

	for (i = 0; i < n_buffers; ++i) {
		struct v4l2_buffer buf;

		CLEAR (buf);

		buf.type        = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		buf.memory      = V4L2_MEMORY_MMAP;
		buf.index       = i;

		if (-1 == xioctl (fd, VIDIOC_QBUF, &buf))
			return errnoexit ("VIDIOC_QBUF");
	}

	type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	if (-1 == xioctl (fd, VIDIOC_STREAMON, &type))
		return errnoexit ("VIDIOC_STREAMON");

	return SUCCESS_LOCAL;
}

int readframeonce(void)
{
	for (;;) {
		fd_set fds;
		struct timeval tv;
		int r;

		FD_ZERO (&fds);
		FD_SET (fd, &fds);

		tv.tv_sec = 2;
		tv.tv_usec = 0;

		r = select (fd + 1, &fds, NULL, NULL, &tv);

		if (-1 == r) {
			if (EINTR == errno)
				continue;

			return errnoexit ("select");
		}

		if (0 == r) {
			LOGE("select timeout");
			return ERROR_LOCAL;

		}

		if (readframe ()==1)
			break;

	}

	return SUCCESS_LOCAL;

}


void processimage (const void *p)
{
		yuyv422toABGRY((unsigned char *)p);
}

int readframe(void)
{

	struct v4l2_buffer buf;
	unsigned int i;

	CLEAR (buf);

	buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	buf.memory = V4L2_MEMORY_MMAP;

	if (-1 == xioctl (fd, VIDIOC_DQBUF, &buf)) {
		switch (errno) {
			case EAGAIN:
				return 0;
			case EIO:
			default:
				return errnoexit ("VIDIOC_DQBUF");
		}
	}

	assert (buf.index < n_buffers);

	processimage (buffers[buf.index].start);

	if (-1 == xioctl (fd, VIDIOC_QBUF, &buf))
		return errnoexit ("VIDIOC_QBUF");

	return 1;
}

int stopcapturing(void)
{
	enum v4l2_buf_type type;

	type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	if (-1 == xioctl (fd, VIDIOC_STREAMOFF, &type))
		return errnoexit ("VIDIOC_STREAMOFF");

	return SUCCESS_LOCAL;

}

int uninitdevice(void)
{
	unsigned int i;

	for (i = 0; i < n_buffers; ++i)
		if (-1 == munmap (buffers[i].start, buffers[i].length))
			return errnoexit ("munmap");

	free (buffers);

	return SUCCESS_LOCAL;
}

int closedevice(void)
{
	if (-1 == close (fd)){
		fd = -1;
		return errnoexit ("close");
	}

	fd = -1;
	return SUCCESS_LOCAL;
}



void yuyv422toABGRY(unsigned char *src)
{

	int width=0;
	int height=0;

	width = IMG_WIDTH;
	height = IMG_HEIGHT;

	int frameSize =width*height*2;

	int i;

	if((!rgb || !ybuf)){
		return;
	}
	int *lrgb = NULL;
	int *lybuf = NULL;

	lrgb = &rgb[0];
	lybuf = &ybuf[0];

	if(yuv_tbl_ready==0){
		for(i=0 ; i<256 ; i++){
			y1192_tbl[i] = 1192*(i-16);
			if(y1192_tbl[i]<0){
				y1192_tbl[i]=0;
			}

			v1634_tbl[i] = 1634*(i-128);
			v833_tbl[i] = 833*(i-128);
			u400_tbl[i] = 400*(i-128);
			u2066_tbl[i] = 2066*(i-128);
		}
		yuv_tbl_ready=1;
	}

	for(i=0 ; i<frameSize ; i+=4){
		unsigned char y1, y2, u, v;
		y1 = src[i];
		u = src[i+1];
		y2 = src[i+2];
		v = src[i+3];

		int y1192_1=y1192_tbl[y1];
		int r1 = (y1192_1 + v1634_tbl[v])>>10;
		int g1 = (y1192_1 - v833_tbl[v] - u400_tbl[u])>>10;
		int b1 = (y1192_1 + u2066_tbl[u])>>10;

		int y1192_2=y1192_tbl[y2];
		int r2 = (y1192_2 + v1634_tbl[v])>>10;
		int g2 = (y1192_2 - v833_tbl[v] - u400_tbl[u])>>10;
		int b2 = (y1192_2 + u2066_tbl[u])>>10;

		r1 = r1>255 ? 255 : r1<0 ? 0 : r1;
		g1 = g1>255 ? 255 : g1<0 ? 0 : g1;
		b1 = b1>255 ? 255 : b1<0 ? 0 : b1;
		r2 = r2>255 ? 255 : r2<0 ? 0 : r2;
		g2 = g2>255 ? 255 : g2<0 ? 0 : g2;
		b2 = b2>255 ? 255 : b2<0 ? 0 : b2;

		*lrgb++ = 0xff000000 | b1<<16 | g1<<8 | r1;
		*lrgb++ = 0xff000000 | b2<<16 | g2<<8 | r2;

		if(lybuf!=NULL){
			*lybuf++ = y1;
			*lybuf++ = y2;
		}
	}

}


void
Java_com_android_factorytest_TestFingerprintView_pixeltobmp( JNIEnv* env,jobject thiz,jobject bitmap){

	jboolean bo;


	AndroidBitmapInfo  info;
	void*              pixels;
	int                ret;
	int i;
	int *colors;

	int width=0;
	int height=0;

	if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
		LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
		return;
	}

	width = info.width;
	height = info.height;

	if(!rgb || !ybuf) return;

	if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
		LOGE("Bitmap format is not RGBA_8888 !");
		return;
	}

	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}

	colors = (int*)pixels;
	int *lrgb =NULL;
	lrgb = &rgb[0];

	for(i=0 ; i<width*height ; i++){
		*colors++ = *lrgb++;
	}

	AndroidBitmap_unlockPixels(env, bitmap);

}

jint
Java_com_android_factorytest_TestFingerprintView_prepareCamera( JNIEnv* env,jobject thiz, jint videoid){

	int ret;

	if(camerabase<0){
		camerabase = checkCamerabase();
	}

	ret = opendevice(camerabase + videoid);

	if(ret != ERROR_LOCAL){
		ret = initdevice();
	}
	if(ret != ERROR_LOCAL){
		ret = startcapturing();

		if(ret != SUCCESS_LOCAL){
			stopcapturing();
			uninitdevice ();
			closedevice ();
			LOGE("device resetted");
		}

	}

	if(ret != ERROR_LOCAL){
		rgb = (int *)malloc(sizeof(int) * (IMG_WIDTH*IMG_HEIGHT));
		ybuf = (int *)malloc(sizeof(int) * (IMG_WIDTH*IMG_HEIGHT));
	}
	return ret;
}



jint
Java_com_android_factorytest_TestFingerprintView_prepareCameraWithBase( JNIEnv* env,jobject thiz, jint videoid, jint videobase){

		int ret;

		camerabase = videobase;

		return Java_com_android_factorytest_TestFingerprintView_prepareCamera(env,thiz,videoid);

}

void
Java_com_android_factorytest_TestFingerprintView_processCamera( JNIEnv* env,
										jobject thiz){

	readframeonce();
}

void
Java_com_android_factorytest_TestFingerprintView_stopCamera(JNIEnv* env,jobject thiz){

	stopcapturing ();

	uninitdevice ();

	closedevice ();

	if(rgb) free(rgb);
	if(ybuf) free(ybuf);

	fd = -1;

}

/*********************************************采集仪接口END***********************************************************/
/**********************************************采集仪FLASH操作接口START***********************************************************/
struct uvc_xu_control_query {
        __u8 unit;
        __u8 selector;
        __u8 query;             /* Video Class-Specific Request Code, */
                                /* defined in linux/usb/video.h A.8.  */
        __u16 size;
        __u8 __user *data;
};

jstring charToJstring(JNIEnv *pInterface, char buf[64]);

#define UVCIOC_CTRL_MAP         _IOWR('u', 0x20, struct uvc_xu_control_mapping)
#define UVCIOC_CTRL_QUERY       _IOWR('u', 0x21, struct uvc_xu_control_query)
#define XU_SONIX_ASIC_CTRL              0x01
#define XU_SONIX_I2C_CTRL               0x02
#define XU_SONIX_SF_READ                0x03

//Sunny 2015-11-17 11:28:11
#define RET_OK  0
#define RET_FAILURE -1

#define LENGTH_OF_SONIX_XU_CTR (3)
#define LENGTH_OF_SONIX_XU_MAP (3)

#define V4L2_CID_BASE_EXTCTR_SONIX                  0x0A0c4501
#define V4L2_CID_BASE_SONIX                         V4L2_CID_BASE_EXTCTR_SONIX
#define V4L2_CID_ASIC_CTRL_SONIX                    V4L2_CID_BASE_SONIX+1
#define V4L2_CID_I2C_CTRL_SONIX                     V4L2_CID_BASE_SONIX+2
#define V4L2_CID_SF_READ_SONIX                      V4L2_CID_BASE_SONIX+3
#define V4L2_CID_LAST_EXTCTR_SONIX                  V4L2_CID_IMG_SETTING_SONIX

#define UVC_GUID_SONIX_USER_HW_CONTROL          {0x70, 0x33, 0xf0, 0x28, 0x11, 0x63, 0x2e, 0x4a, 0xba, 0x2c, 0x68, 0x90, 0xeb, 0x33, 0x40, 0x16}

#define XU_SONIX_ASIC_CTRL              0x01
#define XU_SONIX_I2C_CTRL               0x02
#define XU_SONIX_SF_READ                0x03

#define MAKEWORD(a, b) ((__u16) (((__u8) (a)) | ((__u16) ((__u8) (b))) << 8))

#define XU_SONIX_SF_READ_ADDR           (0xF000 + 128)
#define XU_SONIX_ENCRYPTION_ADDR        (0x0813)
#define XU_SONIX_FINGERPRINT_ADDR       (0x0786)
#define XU_SONIX_SF_READ_ID             (0xF000 + 64)

#define DATA_UNIT_LENGTH                8

#define AB_VER                          0x110


#define FPID	1
//开始读取UVC上的flash
int sonix_start_reading(int dev,int isid)
{
	__u8 ctrldata[11] = {0};
	__u8 ctrli2cdata[8] = {1};
	__u8 ctrlasic[4] = {0};
	LOGE("start reading");
	//flash
	{
		struct uvc_xu_control_query query;
		query.unit = 4;
		query.selector = XU_SONIX_SF_READ;
		query.query = UVC_SET_CUR;
		query.size = 11;
		query.data = (__u8 *)&ctrldata;

		if(isid != 1){
			query.data[0] = XU_SONIX_SF_READ_ADDR & 0xFF;
			query.data[1] = (XU_SONIX_SF_READ_ADDR & 0xFF00) >> 8;
		}
		else{
			query.data[0] = XU_SONIX_SF_READ_ID & 0xFF;
			query.data[1] = (XU_SONIX_SF_READ_ID & 0xFF00) >> 8;
		}
		query.data[2] = DATA_UNIT_LENGTH | 0x80;

		if(ioctl(dev, UVCIOC_CTRL_QUERY, &query) < 0)
		{
			LOGE("UVCIOC_CTRL_QUERY failed %d,%s!",errno,strerror(errno));
//			throw QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
			//QString mess = QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
			//qDebug() << mess;
			return RET_FAILURE;
		}
	}

	return RET_OK;
}

//读取byte
int read_data_byte(int fd, __u8 *data, int len)
{
	__u8 ctrldata[4096] = {0};
	int i = 0;
	struct uvc_xu_control_query query;
	query.unit = 4;
	query.selector = XU_SONIX_SF_READ;
	query.query = UVC_GET_CUR;
	query.size = 11;
	query.data = (__u8 *)&ctrldata;
	//query.data = (__u8 *)&data;
	if(ioctl(fd, UVCIOC_CTRL_QUERY, &query) < 0)
	{
		LOGE("UVCIOC_CTRL_QUERY failed %d,%s!",errno,strerror(errno));
//		throw QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
		//QString mess = QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
		//qDebug() << mess;
		return RET_FAILURE;
	}
	for(i=0;i<len;i++)
		LOGE("0x%x ",ctrldata[i+3]);
	//for(i=0;i<len;i++)
		//LOGE("0x%x ",data[i+3]);
	//LOGE("\n");
	memcpy(data, (ctrldata + 3), len);

	return RET_OK;
}

//读取8byte数据
int read_data_8byte(int fd, __u8 *data)
{
	__u8 ctrldata[11] = {0};
	int i=0;
	struct uvc_xu_control_query query;
	query.unit = 4;
	query.selector = XU_SONIX_SF_READ;
	query.query = UVC_GET_CUR;
	query.size = 11;
	query.data = (__u8 *)&ctrldata;
	LOGE("read 8byte");
	if(ioctl(fd, UVCIOC_CTRL_QUERY, &query) < 0)
	{
//		throw QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
		LOGE("UVCIOC_CTRL_QUERY failed %d,%s!",errno,strerror(errno));
		//QString mess = QString("Acquisition UVCIOC_CTRL_QUERY: %1").arg(strerror(errno));
		//qDebug() << mess;
		return RET_FAILURE;
	}
	for(i=0;i<11;i++)
		LOGE("%c",ctrldata[i]);
	LOGE("\n");
	memcpy(data, (ctrldata + 3), 8);

	return RET_OK;
}
/*Java_com_android_factorytest_TestFpFlash_Test(JNIEnv* env,jobject thiz,jobject buffer,jint len)
{
	__u8 ctrldata[8] = {0};
	int i;
	unsigned char * pBuffer = (unsigned char *)(*env)->GetDirectBufferAddress(env,buffer);
	if(pBuffer == NULL)
	{
		LOGE("create buffer failed");
		return;
	}
	LOGE("open video1");
	opendevice(FPID);
	sonix_start_reading(fd,0);
	//read_data_8byte(fd,ctrldata);
	read_data_byte(fd,pBuffer,len);
	for(i=0;i<len;i++)
		LOGE("0x%x ",pBuffer[i+3]);
	close(fd);
}

Java_com_android_factorytest_TestFpFlash_ReadID(JNIEnv* env,jobject thiz,jobject buffer,jint len)
{
	__u8 ctrldata[8] = {0};
	unsigned char * pBuffer = (unsigned char *)(*env)->GetDirectBufferAddress(env,buffer);
	if(pBuffer == NULL)
	{
			LOGE("create buffer failed");
			return;
	}
	LOGE("open video1");
	opendevice(FPID);
	sonix_start_reading(fd,1);
	read_data_8byte(fd,ctrldata);
	memcpy(pBuffer,ctrldata,8);
	close(fd);
}*/
/**********************************************采集仪FLASH操作接口END***********************************************************/
/**********************************************串口测试START********************************************************************/
static speed_t getBaudrate(jint baudrate)
{
	switch(baudrate) {
	case 0: return B0;
	case 50: return B50;
	case 75: return B75;
	case 110: return B110;
	case 134: return B134;
	case 150: return B150;
	case 200: return B200;
	case 300: return B300;
	case 600: return B600;
	case 1200: return B1200;
	case 1800: return B1800;
	case 2400: return B2400;
	case 4800: return B4800;
	case 9600: return B9600;
	case 19200: return B19200;
	case 38400: return B38400;
	case 57600: return B57600;
	case 115200: return B115200;
	case 230400: return B230400;
	case 460800: return B460800;
	case 500000: return B500000;
	case 576000: return B576000;
	case 921600: return B921600;
	case 1000000: return B1000000;
	case 1152000: return B1152000;
	case 1500000: return B1500000;
	case 2000000: return B2000000;
	case 2500000: return B2500000;
	case 3000000: return B3000000;
	case 3500000: return B3500000;
	case 4000000: return B4000000;
	default: return -1;
	}
}

/*
 * Class:     cedric_serial_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT jobject JNICALL Java_com_android_factorytest_SerialPort_open
  (JNIEnv *env, jobject thiz, jstring path, jint baudrate)
{
	int fd;
	speed_t speed;
	jobject mFileDescriptor;
	LOGE("Java_com_android_factorytest_SerialPort_open %d", baudrate);
	/* Check arguments */
	{
		speed = getBaudrate(baudrate);
		if (speed == -1) {
			/* TODO: throw an exception */
			//LOGE("Invalid baudrate");
			return NULL;
		}
	}

	/* Opening device */
	{
		jboolean iscopy;
		const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
		//LOGD("Opening serial port %s", path_utf);
		fd = open(path_utf, O_RDWR /*| O_DIRECT | O_SYNC*/);
		//LOGD("open() fd = %d", fd);
		(*env)->ReleaseStringUTFChars(env, path, path_utf);
		if (fd == -1)
		{
			/* Throw an exception */
			LOGE("Cannot open port %s",strerror (errno));
			/* TODO: throw an exception */
			return NULL;
		}
	}

	/* Configure device */
	{
		struct termios cfg;
		//LOGD("Configuring serial port");
		if (tcgetattr(fd, &cfg))
		{
			LOGE("tcgetattr() failed");
			close(fd);
			/* TODO: throw an exception */
			return NULL;
		}

		cfmakeraw(&cfg);
		cfsetispeed(&cfg, speed);
		cfsetospeed(&cfg, speed);

		if (tcsetattr(fd, TCSANOW, &cfg))
		{
			//LOGE("tcsetattr() failed");
			close(fd);
			/* TODO: throw an exception */
			return NULL;
		}
	}

	/* Create a corresponding file descriptor */
	{
		jclass cFileDescriptor = (*env)->FindClass(env, "java/io/FileDescriptor");
		jmethodID iFileDescriptor = (*env)->GetMethodID(env, cFileDescriptor, "<init>", "()V");
		jfieldID descriptorID = (*env)->GetFieldID(env, cFileDescriptor, "descriptor", "I");
		mFileDescriptor = (*env)->NewObject(env, cFileDescriptor, iFileDescriptor);
		(*env)->SetIntField(env, mFileDescriptor, descriptorID, (jint)fd);
	}

	return mFileDescriptor;
}

/*
 * Class:     cedric_serial_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_android_factorytest_SerialPort_close
  (JNIEnv *env, jobject thiz)
{
	jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
	jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");

	jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "mFd", "Ljava/io/FileDescriptor;");
	jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");

	jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
	jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);

	//LOGD("close(fd = %d)", descriptor);
	close(descriptor);
}

/**********************************************串口测试END********************************************************************/



jint Java_com_android_factorytest_MainActivity_ReadAdc(JNIEnv* env,jobject thiz)
{
    int dev;
    unsigned int buf;

    dev = open("/dev/adc_test",O_RDONLY);
    if (dev == -1)
    {
        /* Throw an exception */
        LOGE("Cannot open port %s",strerror (errno));
        /* TODO: throw an exception */
//        return NULL;
        return 0;
    }
    //LOGE("Java_com_android_factorytest_MainActivity_ReadAdc:ReadAdc=%d", buf);
    read(dev,&buf,1);
    LOGE("Java_com_android_factorytest_MainActivity_ReadAdc:ReadAdc=%d", buf);
    return buf;
}

jint Java_com_android_factorytest_ResultActivity_ReadAdc(JNIEnv* env,jobject thiz)
{
    int dev;
    unsigned int buf;

    dev = open("/dev/adc_test",O_RDONLY);
    if (dev == -1)
    {
        /* Throw an exception */
        LOGE("Cannot open port %s",strerror (errno));
        /* TODO: throw an exception */
//        return NULL;
        return 0;
    }
    //LOGE("Java_com_android_factorytest_MainActivity_ReadAdc:ReadAdc=%d", buf);
    read(dev,&buf,1);
    LOGE("Java_com_android_factorytest_MainActivity_ReadAdc:ReadAdc=%d", buf);
    return buf;
}

jint JNICALL Java_com_grest_can_1485_TestRS485_1CAN_CANSend()
{
    int s, nbytes, i;
    char device[] = "can0";
    int id, next_option, device_flag=0, id_flag=0,can_dlc;
    char data[8] = {0x53, 0x73, 0x01, 0x11, 0x22, 0x33, 0x45, 0x65};
    struct sockaddr_can addr;
    struct ifreq ifr;
    struct can_frame frame[1];
    static int num = 0;
    if( (num+1)%11 == 0 )
        num++;
    data[2] = (num+1)%11;
    num++;
    //LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend");
    /* create a socket */
    s = socket(PF_CAN, SOCK_RAW, CAN_RAW);
    strcpy(ifr.ifr_name, device );
    ioctl(s, SIOCGIFINDEX, &ifr);
    addr.can_family = AF_CAN;
    addr.can_ifindex = ifr.ifr_ifindex;
    // LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend device=%s",ifr.ifr_name);
    /* bind the socket to a CAN interface */
    //LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend:call bind");
    bind(s, (struct sockaddr *)&addr, sizeof(addr));
    /* Set the filter rules */
    //LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend :call setsockopt");
    setsockopt(s, SOL_CAN_RAW, CAN_RAW_FILTER, NULL, 0);
    /* generate CAN frames */
    id = 0x1;
    can_dlc = 0x8;
    frame[0].can_id = id;
    frame[0].can_dlc = can_dlc;
    LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend:fill data,data[2]=0x%x",data[2]);
    for(i = 0; i < frame[0].can_dlc; i++) {
        //LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend:data[%d]=0x%x",i,data[i]);
        frame[0].data[i] = data[i];

    }
    //LOGE("Java_com_grest_can_1485_TestRS485_1CAN_CANSend:start to send");
    nbytes = write(s, &frame[0], sizeof(frame[0]));
    if (nbytes < 0) {
        LOGE("can raw socket write %d",nbytes);
        close(s);
        return 1;
    }

    /* paranoid check ... */
    if (nbytes < sizeof(struct can_frame)) {
        LOGE("read: incomplete CAN frame\n");
        close(s);
        return 1;
    }
    //sleep(10);
    close(s);
    return 0;
}
jstring JNICALL Java_com_grest_can_1485_TestRS485_1CAN_CANReceive(JNIEnv* env)
{
    int s, nbytes, i;
    char device[] = "can0";
    int id=0, next_option, device_flag=0, id_flag=1;
    struct sockaddr_can addr;
    struct ifreq ifr;
    struct can_frame frame;
    struct can_filter rfilter[1];

    /* create a socket */
    s = socket(PF_CAN, SOCK_RAW, CAN_RAW);
    strcpy(ifr.ifr_name, device);
    /* determine the interface index */
    ioctl(s, SIOCGIFINDEX, &ifr);
    addr.can_family = AF_CAN;
    addr.can_ifindex = ifr.ifr_ifindex;
    /* bind the socket to a CAN interface */
    bind(s, (struct sockaddr *)&addr, sizeof(addr));

    if (id_flag) {
        /* define the filter rules */
        rfilter[0].can_id   = id;
        rfilter[0].can_mask = CAN_SFF_MASK;
        /* Set the filter rules */
        setsockopt(s, SOL_CAN_RAW, CAN_RAW_FILTER, &rfilter, sizeof(rfilter));
    }
    //while(1) {
    /* receive frame */
    nbytes = read(s, &frame, sizeof(frame));
    LOGE("nbytes = %d", nbytes);
    /* printf the received frame */
    if (nbytes > 0) {
        LOGE("%s  %#x  [%d]  ", ifr.ifr_name, frame.can_id, frame.can_dlc);
        for (i = 0; i < frame.can_dlc; i++)
            LOGE("%#x ", frame.data[i]);
    }
    //}
    close(s);
    char buf[64];
    //strncpy(buf,(char*)frame.data,);
    sprintf(buf,"0x%02x 0x%02x 0x%02x 0x%02x 0x%02x 0x%02x 0x%02x 0x%02x",frame.data[0],frame.data[1],frame.data[2],frame.data[3],frame.data[4],frame.data[5],frame.data[6],frame.data[7]);
    LOGE("%s ", buf);
    return charToJstring(env,buf);
}

jstring charToJstring(JNIEnv* env, char *pStr)
{
	int        strLen    = strlen(pStr);
	jclass     jstrObj   = (*env)->FindClass(env, "java/lang/String");
	jmethodID  methodId  = (*env)->GetMethodID(env, jstrObj, NULL, "([BLjava/lang/String;)V");
	jbyteArray byteArray = (*env)->NewByteArray(env, strLen);
	jstring    encode    = (*env)->NewStringUTF(env, "utf-8");

	(*env)->SetByteArrayRegion(env, byteArray, 0, strLen, (jbyte*)pStr);

	return (jstring)(*env)->NewObject(env, jstrObj, methodId, byteArray, encode);
}




