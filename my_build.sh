#!/bin/bash

export PACKAGE_NAME="javairciot"
export IRCIOT_CLASS="jlayerirciot"
export IRCIOT_SOURCE="${IRCIOT_CLASS}.java"
export IRCIOT_BCODE="${IRCIOT_CLASS}.class"
export IRCIOT_BCODE_ADDON="${IRCIOT_CLASS}\$init_constants.class"
export RFC1459_CLASS="jlayerirc"
export RFC1459_SOURCE="${RFC1459_CLASS}.java"
export RFC1459_BCODE="${RFC1459_CLASS}.class"
export RFC1459_BCODE_ADDON="${RFC1459_CLASS}\$init_constants.class"
export CLASSPATH="/usr/share/java"
export CLASSPATH="${CLASSPATH}:/usr/share/java/json-simple-1.1.1.jar"
#export CLASSPATH="${CLASSPATH}:/usr/share/java/json-lib.jar"
#export CLASSPATH="${CLASSPATH}:/usr/share/groovy/lib/groovy-json.jar"
export CLASSPATH="${CLASSPATH}:/usr/share/java/javatuples.jar"
export CLASSPATH="${CLASSPATH}:/usr/share/java/commons-codec.jar"
export BOOT_CLASSPATH="${CLASSPATH}"
export PASE_DEFAULT_UTF8=Y
export DEBIAN_PACKAGES="libjavatuples-java libjson-simple-java"
export DEBIAN_PACKAGES="${DEBIAN_PACKAGES} libcommons-codec-java"
export DEBIAN_PACKAGES="${DEBIAN_PACKAGES} maven"
export COMPILE_ARGS="-Xdiags:verbose"
export COMPILE_ARGS="${COMPILE_ARGS} -Xlint" # Warnings
#export MAVEN_ARGS="-X" # Maven detailed Warnings
export MAVEN_ARGS="-DsourceEncoding=UTF-8 -Dfile.encoding=UTF-8"
export MAVEN_ARGS="${MAVEN_ARGS} -DdefaultCharacterEncoding=UTF-8"
#export GRADLE_ARGS="${GRADLE_ARGS} --warning-mode all" # Warnings
export GRADLE_ARGS="${GRADLE_ARGS} --warning-mode none"
export BINARY_APT_GET="/usr/bin/apt-get"
export BINARY_DIRNAME="/usr/bin/dirname"
export BINARY_JAVAC="/usr/bin/javac"
export BINARY_DPKG="/usr/bin/dpkg"
export BINARY_TR="/usr/bin/tr"
export BINARY_GREP="/bin/grep"
export BINARY_JAR="/usr/bin/jar"
export BINARY_SED="/bin/sed"
export BINARY_CP="/bin/cp"
export BINARY_RM="/bin/rm"
export BINARY_LN="/bin/ln"
export BINARY_RMDIR="/bin/rmdir"
export BINARY_MKDIR="/bin/mkdir"
export BINARY_MVN="/usr/bin/mvn"

if [ -x "${BINARY_DIRNAME}" ]; then
 cd "$("${BINARY_DIRNAME}" "${0}" 2>/dev/null)"
fi

if [ "x${1}x" == "xx" ]; then
 echo -ne "Usage: ${0} [ build | maven | gradle | test | clear ]\n\n"
 echo -ne " build  -- build Java IRC-IoT library with simple Java compiling\n"
 echo -ne " maven  -- build Java IRC-IoT library using Maven system\n"
 echo -ne " gradle -- build Java IRC-IoT ready for installation on Android\n"
 echo -ne " test   -- build Java IRC-IoT library and all test examples\n"
 echo -ne " clear  -- clear all distribution from *.class and other files\n\n"
 exit 0
elif [ "x${1}x" != "xbuildx" \
    -a "x${1}x" != "xmavenx" \
    -a "x${1}x" != "xgradlex" \
    -a "x${1}x" != "xandroidx" \
    -a "x${1}x" != "xclearx" \
    -a "x${1}x" != "xtestx" ]; then
 echo -ne "Error: incorrect parameter\n" ; exit 1
fi

if [ "x${1}x" == "xgradlex" -o "x${1}x" == "xandroidx" ]; then
 if [ ! -x ./gradlew ]; then
  echo "Gradle not found inside the distribution!" ; exit 1 ; fi
 if [ "x${ANDROID_SDK_ROOT}x" == "xx" -a "x${ANDROID_HOME}x" != "xx" ]; then
  export ANDROID_SDK_ROOT="${ANDROID_HOME}" ; fi
 if [ "x${ANDROID_SDK_ROOT}x" != "xx" -a "x${ANDROID_HOME}x" == "xx" ]; then
  export ANDROID_HOME="${ANDROID_SDK_ROOT}" ; fi
 if [ "x${ANDROID_HOME}x" == "xx" ]; then
  echo "Warning: Environment variables ANDROID_HOME and ANDROID_SDK_ROOT are empty."
  echo -ne "Trying to find Android SDK by fixed paths ... "
  for TEST_PATH in \
   ~"/Android/Sdk" \
   ~"/Library/Adroid/sdk" \
   ~"/sdk/android-sdk-linux" \
   "/usr/lib/android-sdk" \
   "/usr/lib/android/sdk" \
   "/usr/lib/Android/Sdk" \
   "/usr/local/Android/Sdk" \
   "/usr/local/Android/SDK" \
   "/usr/local/android/SDK" \
   "/usr/local/android/sdk" \
   "/opt/android-sdk-linux" \
   "/opt/android/sdk" \
   "/opt/android/Sdk" \
   "/opt/Android/SDK" ; do
   export ANDROID_ADB="${TEST_PATH}/platform-tools/adb"
   if [ -x "${ANDROID_ADB}" ]; then
    export ANDROID_HOME="${TEST_PATH}"
    export PATH="${PATH}:${ANDROID_HOME}/platform-tools"
    export ANDROID_SDK_ROOT="${TEST_PATH}"
    echo -ne "FOUND! '\033[1m${ANDROID_HOME}\033[0m'\n" ; break ; fi
  done ; fi
 if [ "x${ANDROID_HOME}x" == "xx" ]; then
  echo "FAILED! Cannot find, exiting ..." ; exit 1 ; fi
 if [ -x "${ANDROID_HOME}/platform-tools/adb" ]; then
  export ANDROID_ADB="${ANDROID_HOME}/platform-tools/adb" ; fi
 export ANDROID_SDK="${ANDROID_HOME}"
 if [ "x${ANDROID_NDK_HOME}x" == "xx" -a "x${ANDROID_NDK}x" != "xx" ]; then
  export ANDROID_NDK_HOME="${ANDROID_NDK}" ; fi
 if [ "x${ANDROID_NDK_HOME}x" != "xx" -a "x${ANDROID_NDK}x" == "xx" ]; then
  export ANDROID_NDK="${ANDROID_NDK_HOME}" ; fi
 if [ "x${ANDROID_NDK}x" == "xx" ]; then
  echo "Warning: Environment variable ANDROID_NDK and ANDROID_NDK_HOME are empty."
  echo -ne "Trying to find Android NDK by fixed paths ... "
  for TEST_PATH in \
   ~"/Android/Ndk" \
   ~"/Library/Adroid/ndk" \
   ~"/Android/Sdk/ndk-bundle" \
   "/usr/lib/android-ndk" \
   "/usr/lib/android/ndk" \
   "/usr/lib/Android/Ndk" \
   "/usr/local/Android/Ndk" \
   "/usr/local/Android/NDK" \
   "/usr/local/android/NDK" \
   "/usr/local/android/ndk" ; do
   if [ -x "${TEST_PATH}/ndk-build" ]; then
    export ANDROID_NDK="${TEST_PATH}"
    export ANDROID_NDK_HOME="${TEST_PATH}"
    export PATH="${PATH}:${ANDROID_NDK}"
    echo -ne "FOUND! '\033[1m${ANDROID_NDK}\033[0m'\n" ; break ; fi
  done ; fi
 export SDK_VERSION="$("${ANDROID_ADB}" | "${BINARY_GREP}" '^Version\ [1-9]*\.' | \
  "${BINARY_SED}" 's/Version\ \([0-9]*\)\..*/\1/g')"
 if [ "x${SDK_VERSION}x" != "xx" ]; then
  echo -ne "Version '\033[1m${SDK_VERSION}\033[0m' of Android SDK detected"
  echo -ne " at: '${ANDROID_HOME}'.\n" ; fi
 ./gradlew -b ./build.gradle wrapper ${GRADLE_ARGS}
 echo -ne "\n\033[1;41mThe script for building is incomplete,"
 echo -ne " try this option in the next version\033[0m\n\n"
 exit 0
fi

if [ "x${1}x" == "xmavenx" ]; then
 if [ -d "./target" ]; then
  "${BINARY_RM}" -rf "./target" 2>/dev/null ; fi
 if [ -f /etc/debian_version ]; then
  "${BINARY_APT_GET}" -yqf install maven 1>/dev/null 2>/dev/null ; fi
 if [ ! -x "${BINARY_MVN}" ]; then
  echo "No executable file: '${BINARY_MVN}', exiting... " ; exit 1 ; fi
 "${BINARY_MVN}" ${MAVEN_ARGS} ; ERRLV8=$?
 if [ ${ERRLV8} -eq 0 ]; then
  if [ -f "./target/classes/${PACKAGE_NAME}/${IRCIOT_BCODE}" ]; then
   # maven builded classes
   "${BINARY_LN}" -s "./target/classes/${PACKAGE_NAME}" . 2>/dev/null  ; fi
 echo -ne "\033[1;32mAll OK\033[0m (maven)\n" ; exit 0 ; fi
 exit 1
fi

for THE_BINARY in "${BINARY_APT_GET}" "${BINARY_JAVAC}" "${BINARY_LN}" \
 "${BINARY_DPKG}" "${BINARY_TR}" "${BINARY_GREP}" "${BINARY_JAR}" \
 "${BINARY_CP}" "${BINARY_RM}" "${BINARY_RMDIR}" "${BINARY_MKDIR}" ; do
 if [ ! -x "${THE_BINARY}" ]; then
  echo "No executable file: '${THE_BINARY}', exiting... " ; exit 1 ; fi
done

for SOURCE_FILE in "${IRCIOT_SOURCE}" "${RFC1459_SOURCE}" ; do
 if [ ! -f "./src/${SOURCE_FILE}" ]; then
  echo "No file: '${ISOURCE_FILE}', exiting... " ; exit 1 ; fi
done
 
for BYTECODE_FILE in "${IRCIOT_CLASS}" "${RFC1459_CLASS}" ; do
 "${BINARY_RM}" -f "./build/${PACKAGE_NAME}/${BYTECODE_FILE}"*".class" 2>/dev/null
done

if [ "x${1}" == "xclear" ]; then
 if [ -d "./target" ]; then
  "${BINARY_RM}" -rf "./target" 2>/dev/null ; fi
 if [ -d "./build/intermediates" ]; then
  "${BINARY_RM}" -rf "./build/intermediates" 2>/dev/null ; fi
 if [ -d "./build/android-profile" ]; then
  "${BINARY_RM}" -rf "./build/android-profile" 2>/dev/null ; fi
 if [ -d "./.gradle" ]; then
  "${BINARY_RM}" -rf "./.gradle" 2>/dev/null ; fi
 if [ -d "./examples/.gradle" ]; then
  "${BINARY_RM}" -rf "./examples/.gradle" 2>/dev/null ; fi
 "${BINARY_RM}" -f "./src/"*~ 2>/dev/null
 if [ -d "./src/.gradle" ]; then
  "${BINARY_RM}" -f "./src/.gradle" 2>/dev/null ; fi
 "${BINARY_RM}" -f "./javairciot" 2>/dev/null
 "${BINARY_RM}" -f "./build/"*.jar 2>/dev/null
 "${BINARY_RM}" -f "./build/${PACKAGE_NAME}/"*.class 2>/dev/null
 "${BINARY_RMDIR}" "./build/${PACKAGE_NAME}/" 2>/dev/null
 "${BINARY_RM}" -f *.jar *.class *~ 2>/dev/null ; exit 0 ; fi
 
if [ -f /etc/debian_version ]; then
 for DEBIAN_PACKAGE in ${DEBIAN_PACKAGES} ; do
  "${BINARY_DPKG}" -l | \
  "${BINARY_GREP}" " ${DEBIAN_PACKAGE} " 1>/dev/null 2>/dev/null ; ERRLV=$?
  if [ ${ERRLV} -ne 0 ]; then
   "${BINARY_APT_GET}" install "${DEBIAN_PACKAGE}" ; fi
 done
fi

for JAR_PACKAGE in $(echo "${CLASSPATH}" | "${BINARY_TR}" ':' ' ') ; do
 if [ ! -f "${JAR_PACKAGE}" ]; then
  if [ -d "${JAR_PACKAGE}" ]; then continue ; fi
  echo "No file: '${JAR_PACKAGE}', exiting... " ; exit 1 ; fi
done

"${BINARY_JAVAC}" ${COMPILE_ARGS} -d ./build -cp ${CLASSPATH} \
 "./src/${IRCIOT_SOURCE}" "./src/${RFC1459_SOURCE}" ; ERRLV1=$?
if [ ${ERRLV1} -ne 0 ]; then
 echo "Error compiling Java IRC-IoT classes, exiting... "
 exit 1
else
 cd build
 "${BINARY_RM}" -f "./${PACKAGE_NAME}.jar" 2>/dev/null
 "${BINARY_JAR}" -cvf "./${PACKAGE_NAME}.jar" \
  "./${PACKAGE_NAME}/${IRCIOT_BCODE}" \
  "./${PACKAGE_NAME}/${IRCIOT_BCODE_ADDON}" \
  "./${PACKAGE_NAME}/${RFC1459_BCODE}" \
  "./${PACKAGE_NAME}/${RFC1459_BCODE_ADDON}"
 cd ..
fi

export ERRLV3=0
if [ ! -h "./${PACKAGE_NAME}" ]; then
 if [ -d "./build/${PACKAGE_NAME}" ]; then
  # simple builded classes
  "${BINARY_LN}" -s "./build/${PACKAGE_NAME}" . 2>/dev/null
 elif [ -d "./target/classes/${PACKAGE_NAME}" ]; then
  # Maven builded classes
  "${BINARY_LN}" -s "./target/classes/${PACKAGE_NAME}" . 2>/dev/null
 else
  echo "Cannot find Java IRC-IoT classes to compile tests" ; exit 1 ; fi
fi
if [ "x${1}" == "xtest" ]; then
 export CLASSPATH="./build/${PACKAGE_NAME}.jar:${CLASSPATH}"
 export BOOT_CLASSPATH="${CLASSPATH}"
 echo "Trying to compile examples ..."
 for TEST_FILE in ./examples/*.java ; do
  if [ "x${TEST_FILE}" == "x./examples/*.java" ]; then
   echo "Warning: no files in examples directory" ; break ; fi
  "${BINARY_JAVAC}" -d . ${COMPILE_ARGS} \
    -cp ${CLASSPATH} "${TEST_FILE}" ; ERRLV3=$?
  if [ ${ERRLV3} -ne 0 ]; then
   echo "Error compiling test file '${TEST_FILE}', exiting... " ; exit 1 ; fi
 done
fi
  
if [ ${ERRLV1} -eq 0 -a ${ERRLV3} -eq 0 ]; then
 echo -ne "\033[1;32mAll OK\033[0m (build)\n" ; fi

exit 0
