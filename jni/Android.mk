LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := factorytest
LOCAL_SRC_FILES := factorytest.c
LOCAL_LDLIBS    := -llog -ljnigraphics 
include $(BUILD_SHARED_LIBRARY)
