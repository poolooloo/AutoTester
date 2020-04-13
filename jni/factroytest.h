/*
 * factroytest.h
 *
 *  Created on: 2015Äê10ÔÂ28ÈÕ
 *      Author: huzizhen
 */

#ifndef FACTROYTEST_H_
#define FACTROYTEST_H_
#include <jni.h>
#include <android/log.h>
#include <android/bitmap.h>

#include <string.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <fcntl.h>              /* low-level i/o */
#include <unistd.h>
#include <errno.h>
#include <malloc.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/mman.h>
#include <sys/ioctl.h>

#include <asm/types.h>          /* for videodev2.h */

//#include <linux/uvcvideo.h>
#include <linux/videodev2.h>
#include <linux/usbdevice_fs.h>

#include "video.h"
//#include <video.h>


#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>
#include <errno.h>

#define  LOG_TAG    "FactoryTest"
#define  LOGE(...)  __android_log_print(6,LOG_TAG,__VA_ARGS__)
//#define  LOG_TAG    "WebCam"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
//#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#define CLEAR(x) memset (&(x), 0, sizeof (x))

#define IMG_WIDTH 800
#define IMG_HEIGHT 600

#define ERROR_LOCAL -1
#define SUCCESS_LOCAL 0

struct buffer {
        void *                  start;
        size_t                  length;
};

static char            dev_name[16];
static int              fd              = -1;
struct buffer *         buffers         = NULL;
static unsigned int     n_buffers       = 0;

int camerabase = -1;

int *rgb = NULL;
int *ybuf = NULL;

int yuv_tbl_ready=0;
int y1192_tbl[256];
int v1634_tbl[256];
int v833_tbl[256];
int u400_tbl[256];
int u2066_tbl[256];

int errnoexit(const char *s);

int xioctl(int fd, int request, void *arg);

int checkCamerabase(void);
int opendevice(int videoid);
int initdevice(void);
int initmmap(void);
int startcapturing(void);

int readframeonce(void);
int readframe(void);
void processimage (const void *p);

int stopcapturing(void);
int uninitdevice(void);
int closedevice(void);

void yuyv422toABGRY(unsigned char *src);

jint Java_com_android_factorytest_TestFingerprintView_prepareCamera( JNIEnv* env,jobject thiz, jint videoid);
jint Java_com_android_factorytest_TestFingerprintView_prepareCameraWithBase( JNIEnv* env,jobject thiz, jint videoid, jint videobase);
void Java_com_android_factorytest_TestFingerprintView_processCamera( JNIEnv* env,jobject thiz);
void Java_com_android_factorytest_TestFingerprintView_stopCamera(JNIEnv* env,jobject thiz);
void Java_com_android_factorytest_TestFingerprintView_pixeltobmp( JNIEnv* env,jobject thiz,jobject bitmap);



int XU_ASIC_Get_Data(int fd, int Addr, __uint8_t *uData);
int XU_ASIC_Set_Data(int fd, int Addr, __uint8_t uData);

#endif /* FACTROYTEST_H_ */
