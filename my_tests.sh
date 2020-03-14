#!/bin/bash

export PACKAGE_NAME="javairciot"
export BINARY_TR="/usr/bin/tr"
export BINARY_JAVA="/usr/bin/java"
export BINARY_GREP="/bin/grep"
export BINARY_AWK="/usr/bin/awk"
export BINARY_BASENAME="/usr/bin/basename"
export BINARY_DIRNAME="/usr/bin/dirname"
export SCRIPT_BUILD="./my_build.sh"
export CLASSPATH="/usr/share/java"
export CLASSPATH="${CLASSPATH}:/usr/share/java/json-simple-1.1.1.jar"
export CLASSPATH="${CLASSPATH}:/usr/share/java/javatuples.jar"
export JAVA_OPTIONS="-Djava.net.preferIPv4Stack=true"

if [ -x "${BINARY_DIRNAME}" ]; then
 cd "$("${BINARY_DIRNAME}" "${0}" 2>/dev/null)"
fi

for THE_BINARY in "${BINARY_JAVA}" "${BINARY_BASENAME}" \
 "${BINARY_DIRNAME}" "${BINARY_GREP}" "${BINARY_TR}" \
 "${BINARY_AWK}" "${SCRIPT_BUILD}" ; do
 if [ ! -x "${THE_BINARY}" ]; then
  echo "No executable file: '${THE_BINARY}', exiting... " ; exit 1 ; fi
done

export TESTS=""
for TEST_FILE in ./examples/*.java ; do
 if [ "x${TEST_FILE}" == "x./examples/*.java" ]; then
  echo "Error: no files in examples directory" ; exit 1 ; fi
  export TESTS="${TESTS} $("${BINARY_BASENAME}" "${TEST_FILE}" '.java')"
done

export TEST="$(echo "${1}" | "${BINARY_TR}" -d ' ' 2>/dev/null)"
if [ "x${TEST}x" == "xx" ]; then
 echo -ne "Usage: ${0} [<test-name>]\n"
 echo -ne "Where <test-name> is one of tests:\n\n"
 echo -ne "${TESTS}\n\n" ; exit 0
elif [ ! -f "./examples/${TEST}.java" ]; then
 echo "No such test example: '${TEST}' ..." ; exit 1
else
 export TEST_CLASS="$("${BINARY_GREP}" "^class " \
 "./examples/${TEST}.java" | \
 "${BINARY_AWK}" '{printf "%s", $2}' 2>/dev/null)" ; fi
if [ ! -f "./${TEST_CLASS}.class" \
    -a -f "./examples/${TEST}.java" ]; then
 echo "File './examples/${TEST}.java' exists, but not compiled yet."
 echo "Trying to build all test examples by: '${SCRIPT_BUILD} test'"
 "${SCRIPT_BUILD}" test
fi
if [ ! -h "./${PACKAGE_NAME}" ]; then
 echo -ne "Symlink './${PACKAGE_NAME}' not found, "
 echo -ne "may be Java IRC-IoT classes not compiled yet.\n"
 echo "Trying to build Java IRC-IoT classes by: '${SCRIPT_BUILD} build'"
 "${SCRIPT_BUILD}" build
fi
if [ -f "./${TEST_CLASS}.class" ]; then
 echo -ne "Trying to Run: '\033[1m./${TEST_CLASS}.class\033[0m' ...\n\n"
 export CLASSPATH="${CLASSPATH}:."
 "${BINARY_JAVA}" ${JAVA_OPTIONS} "${TEST_CLASS}" ; ERRLV=$?
 if [ ${ERRLV} -eq 0 ]; then echo -ne "\n\033[1;32mAll OK\033[0m (test)\n"
 exit 0 ; fi
 exit 1
else
 echo "Error: Cannot build './examples/${TEST}.java' to './${TEST_CLASS}.class'."
fi
  
exit 0
