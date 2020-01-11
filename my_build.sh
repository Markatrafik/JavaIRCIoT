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
export PASE_DEFAULT_UTF8=Y
export DEBIAN_PACKAGES="libjavatuples-java libjson-simple-java maven"
export COMPILE_ARGS="-Xdiags:verbose"
export COMPILE_ARGS="${COMPILE_ARGS} -Xlint" # Warnings
export MAVEN_ARGS="-DsourceEncoding=UTF-8 -Dfile.encoding=UTF-8"
export MAVEN_ARGS="${MAVEN_ARGS} -DdefaultCharacterEncoding=UTF-8"
export BINARY_APT_GET="/usr/bin/apt-get"
export BINARY_JAVAC="/usr/bin/javac"
export BINARY_DPKG="/usr/bin/dpkg"
export BINARY_TR="/usr/bin/tr"
export BINARY_GREP="/bin/grep"
export BINARY_JAR="/usr/bin/jar"
export BINARY_CP="/bin/cp"
export BINARY_RM="/bin/rm"
export BINARY_LN="/bin/ln"
export BINARY_RMDIR="/bin/rmdir"
export BINARY_MKDIR="/bin/mkdir"
export BINARY_MVN="/usr/bin/mvn"

if [ "x${1}x" == "xx" ]; then
 echo -ne "Usage: ${0} [ build | maven | test | clear ]\n\n"
 echo -ne " build -- build Java IRC-IoT library with simple Java compiling\n"
 echo -ne " maven -- build Java IRC-IoT library using Maven system\n"
 echo -ne " test  -- build Java IRC-IoT library and all test examples\n"
 echo -ne " clear -- clear all distribution from *.class and other\n\n"
 exit 0
elif [ "x${1}x" != "xbuildx" \
    -a "x${1}x" != "xmavenx" \
    -a "x${1}x" != "xclearx" \
    -a "x${1}x" != "xtestx" ]; then
 echo -ne "Error: incorrect parameter\n" ; exit 1
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
 "${BINARY_RM}" -f "./src/"*~ 2>/dev/null
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
