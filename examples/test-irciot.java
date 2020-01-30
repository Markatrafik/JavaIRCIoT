/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import java.lang.*;
import javairciot.*;

class test_javairciot extends javairciot.jlayerirciot {

  public static void irc_testing() {
    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRCIoT (protocol) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);
  };

  public static void irc_checks() {
    jlayerirciot my_irciot = new jlayerirciot();
    String[] my_addresses = new String[] { "", "Hello@World",
      "server@location1/device@location2", "@location1/device",
      "t@t", "wekfj24er24'f,f4ef_ ed'e2@@@@" };
    System.out.println("");
    for (int my_idx = 0;my_idx < my_addresses.length;my_idx++) {
      String my_str = my_addresses[my_idx];
      System.out.print("Test is_irciot_address_(\"" + my_str + "\") = ");
      System.out.println(my_irciot.is_irciot_address_(my_str));
    };
  };

  public static void main(String args[]) {
    irc_testing();
    irc_checks();
  };


}


