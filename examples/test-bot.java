/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import java.lang.*;
import javairciot.jlayerirc;

class test_BOT extends javairciot.jlayerirc {

  public static void main(String args[]) {
    System.out.println("Starting JLayerIRC bot ...");
    jlayerirc my_irc = new jlayerirc();
    my_irc.irc_port = 6667;
    my_irc.irc_server = "chat.freenode.net";
    my_irc.irc_channel = "#Berdsk";
    my_irc.irc_debug = true;
    my_irc.irc_talk_with_strangers = true;
    my_irc.irc_define_nick_("MyxTest1");
    my_irc.irc_ident = false;
    my_irc.start_IRC_();
    my_irc.irc_sleep_(20000);
    my_irc.stop_IRC_();
  }

}
