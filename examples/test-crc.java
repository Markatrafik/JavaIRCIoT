/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import java.lang.*;
import javairciot.jlayerirciot;

class test_CRC extends javairciot.jlayerirciot {
  
  public static void crc_testing() {
    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRCIoT (protocol) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);    
  }

  public static void main(String args[]) {
    crc_testing();

  }
}

