#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_kotlin_livedataroomretrofitkotlindemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_kotlin_livedataroomretrofitkotlindemo_MainActivity_encodeStringFromJNI(
        JNIEnv *env,
        jobject, /* this */
        jstring stringNeedToEncode) {
    if(stringNeedToEncode == NULL)
        return NULL;
    jsize len = env->GetStringLength(stringNeedToEncode);
    char buff[128] = "hello ";
    char* pBuff = buff + 6;
    env->GetStringUTFRegion(stringNeedToEncode,0,len,pBuff);
    return env->NewStringUTF(buff);
}

