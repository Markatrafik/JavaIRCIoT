/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */
 
import JavaIRCIoT.JLayerIRC;

class JLayerIRC_test extends JavaIRCIoT.JLayerIRC {

  public static void main(String args[]) {
  
    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRC (RFC-1459) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);
    // System.out.println(CONST.default_mtu);
    // System.out.println(CONST.code_MAPEND);

  }

}
