/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import JavaIRCIoT.JLayerIRCIoT;

class JLayerIRCIoT_test extends JavaIRCIoT.JLayerIRCIoT {

  public static void main(String args[]) {

    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRCIoT (protocol) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);

  }
}

