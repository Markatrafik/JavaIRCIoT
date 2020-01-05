/*
 * JavaIRCIoT (JLayerIRC class)
 *
 * Copyright (c) 2020 Alexey Y. Woronov
 *
 * By using this file, you agree to the terms and conditions set
 * forth in the LICENSE file which can be found at the top level
 * of this package
 *
 * Authors:
 *   Alexey Y. Woronov <alexey@woronov.ru>
 */
 
package JavaIRCIoT;

public class JLayerIRC {

  // Those Global options override default behavior and memory usage:
  //
  public static final boolean DO_debug_library  = false;

  public static final class init_CONST {
   //
   public String irciot_library_version = "0.0.165";
   //
   public String irciot_protocol_version = "0.3.29";
   //
   public boolean irc_default_debug = DO_debug_library;
   //
   public String irc_default_nick = "MyBot";
   public String irc_default_info = "IRC-IoT Bot";
   public String irc_default_quit = "Bye!";
   //
   public String irc_default_server = "irc-iot.nsk.ru";
   public int    irc_default_port  = 6667;
   public String irc_default_password = null;
   public boolean irc_default_ssl  = false;
   //
   public String irc_default_proxy = null;
   public String irc_default_proxy_server = null;
   public int    irc_default_proxy_port = 0; // 0 -- no port
   public String irc_default_proxy_password = null;
   //
   public String irc_default_channel = "#myhome";
   public String irc_default_chankey = null;
   //
   // User options:
   public int irc_aop   = 101; // give (+o) him channel operator status
   public int irc_aban  = 102; // ban (+b) him on these channels
   public int irc_avo   = 103; // give (+v) him a voice on channels
   public int irc_akick = 130; // kick it from these channels
   public int irc_adeop = 131; // take away (-o) his channel operator status
   public int irc_unban = 132; // unban (-b) mask on channels when banned
   public int irc_adevo = 133; // take away (-v) his voice on channels
   //
   // 0. Unique User ID
   // 1. IRC User Mask
   // 2. IRC Channel Name
   // 3. User Options
   // 4. Encryption Private or Secret Key
   // 5. Blockchain Private Key
   // 6. Last Message ID
   // 7. Encryption Key Timeout
   // 8. Blockchain Key Timeout
   // 9. My last Message ID
   //
   // Deault Message ID pipeline size:
   public int irc_default_mid_pipeline_size = 16;
   //
   public boolean irc_default_talk_with_strangers = false;
   public int irc_first_temporal_vuid = 1000;
   //
   public int api_GET_LMID = 101; // Get last Message ID
   public int api_SET_LMID = 102; // Set last Message ID
   public int api_GET_OMID = 111; // Get Own last Message ID
   public int api_SET_OMID = 112; // Set Own last Message ID
   public int api_GET_EKEY = 301; // Get Encryption Key
   public int api_SET_EKEY = 302; // Set Encryption Key
   public int api_GET_EKTO = 351; // Get Encryption Key Timeout
   public int api_SET_EKTO = 352; // Set Encyrption Key Timeout
   public int api_GET_BKEY = 501; // Get Blockchain key
   public int api_SET_BKEY = 502; // Set Blockchain Key
   public int api_GET_BKTO = 551; // Get Blockchain Key Timeout
   public int api_SET_BKTO = 552; // Set Blockchain Key Timeout
   public int api_GET_VUID = 700; // Get list of Virutal User IDs
   //
   public String api_vuid_cfg = "c"; // VUID prefix for users from config
   public String api_vuid_tmp = "t"; // VUID prefix for temporal users
   public String api_vuid_srv = "s"; // VUID prefix for IRC-IoT Services
   public String api_vuid_all = "*"; // Means All users VUIDs when sending messages
   //
   public String api_vuid_self = "c0"; // Default preconfigured VUID
   //
   // Basic IRC-IoT Services
   //
   public String api_vuid_CRS = "sC"; // Сryptographic Repository Service
   public String api_vuid_GDS = "sD"; // Global Dictionary Service
   public String api_vuid_GRS = "sR"; // Global Resolving Service
   public String api_vuid_GTS = "sT"; // Global Time Service
   //
   public String api_vuid_RoS = "sr"; // Primary Routing Service
   //
   public int irc_queue_input  = 0;
   public int irc_queue_output = 1;
   //
   public int irc_recon_steps  = 8;
   //
   public int irc_buffer_size  = 2048;
   //
   public String[] irc_layer_modes = { "CLIENT", "SERVICE", "SERVER" };
   //
   public int irc_default_nick_retry = 3600; // in seconds
   //
   // According RFC 1459
   //
   public String irc_ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
   public String irc_ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   public String irc_ascii_letters = irc_ascii_lowercase + irc_ascii_uppercase;
   public String irc_ascii_digits = "0123456789";
   public String irc_special_chars = "-[]\\`^{}";
   public String irc_nick_first_char = irc_ascii_letters + irc_special_chars;
   public String irc_nick_chars = irc_ascii_letters + irc_ascii_digits + irc_special_chars;
   public String irc_mode_add = "+";
   public String irc_mode_del = "-";
   public String irc_change_modes = irc_mode_add + irc_mode_del;
   public String irc_umode_op = "o";
   public String irc_umode_voice = "v";
   public String irc_umode_ban = "b";
   public String irc_user_modes = irc_umode_op + irc_umode_voice + irc_umode_ban;
   public String irc_channel_modes = "psitnm";
   public String irc_extra_modes = "lk";
   public String irc_all_modes = irc_user_modes + irc_channel_modes + irc_extra_modes;
   public String irc_all_modes_chars = irc_change_modes + irc_all_modes;
   //
   public int irc_max_nick_length = 15;
   //
   public String irc_draft = "Undernet";
   //
   // "RFC1459", "Undernet", "Unreal", "Bahamut",   "Inspl", "Hybrid",
   // "RusNet",  "Shadow",   "ircu",   "Nefarious", "Rock",  "Synchronet",
   // "solid",   "PieXus",   "ratbox", "Charybdis", "pure",  "Rubl",
   // "ngl",     "ConfRoom", "pircd"
   //
   public int default_mtu = 480;
   //
   public String code_WELCOME            = "001";
   public String code_YOURHOST           = "002";
   public String code_CREATED            = "003";
   public String code_MYINFO             = "004";
   public String code_FEATURELIST        = "005"; // Unknown
   public String code_MAP                = irc_draft == "Undernet" ? "005" : null;
   public String code_MAPMORE            = irc_draft == "Undernet" ? "006" : null;
   public String code_MAPEND             = irc_draft == "Undernet" ? "007" : null;
   public String code_SNOMASK            = irc_draft == "Undernet" ? "008" : null;
   public String code_STATMEMTOT         = irc_draft == "Undernet" ? "009" : null;
   public String code_STATMEM            = irc_draft == "Undernet" ? "010" : null;
   public String code_TRACELINK          = "200";
   public String code_TRACECONNECTING    = "201";
   public String code_TRACEHANDSHAKE     = "202";
   public String code_TRACEUNKNOWN       = "203";
   public String code_TRACEOPERATOR      = "204";
   public String code_TRACEUSER          = "205";
   public String code_TRACESERVER        = "206";
   public String code_TRACESERVICE       = "207";
   public String code_TRACENEWTYPE       = "208";
   public String code_TRACECLASS         = "209";
   public String code_TRACERECONNECT     = "210";
   public String code_STATSLINKINFO      = "211";
   public String code_STATSCOMMANDS      = "212";
   public String code_STATSCLINE         = "213";
   public String code_STATSNLINE         = "214";
   public String code_STATSILINE         = "215";
   public String code_STATSKLINE         = "216";
   public String code_STATSQLINE         = "217";
   public String code_STATSYLINE         = "218";
   public String code_ENDOFSTATS         = "219";
   public String code_STATSBLINE         = irc_draft == "Unreal"   ? "220" : null;
   public String code_UMODEIS            = "221";
   public String code_SQLINE_NICK        = irc_draft == "Unreal"   ? "222" : null;
   public String code_STATSGLINE         = irc_draft == "Unreal"   ? "223" : null;
   public String code_STATSTLINE         = irc_draft == "Unreal"   ? "224" : null;
   public String code_STATSELINE         = irc_draft == "Unreal"   ? "225" : null;
   public String code_STATSVLINE         = irc_draft == "Unreal"   ? "227" : null;
   public String code_SERVICEINFO        = "231";
   public String code_ENDOFSERVICES      = "232";
   public String code_SERVICE            = "233";
   public String code_SERVLIST           = "234";
   public String code_SERVLISTEND        = "235";
   public String code_STATSLLINE         = "241";
   public String code_STATSUPTIME        = "242";
   public String code_STATSOLINE         = "243";
   public String code_STATSHLINE         = "244";
   public String code_STATSSLINE         = "245"; // Unknown
   public String code_STATSXLINE         = irc_draft == "Unreal"   ? "247" : null;
   public String code_STATSULINE         = irc_draft == "Undernet" ? "248" : null;
   public String code_STATSDEBUG         = "249"; // Unknown
   public String code_LUSERCONNS         = "250";
   public String code_LUSERCLIENT        = "251";
   public String code_LUSEROP            = "252";
   public String code_LUSERUNKNOWN       = "253";
   public String code_LUSERCHANNELS      = "254";
   public String code_LUSERME            = "255";
   public String code_ADMINME            = "256";
   public String code_ADMINLOC1          = "257";
   public String code_ADMINLOC2          = "258";
   public String code_ADMINEMAIL         = "259";
   public String code_TRACELOG           = "261";
   public String code_ENDOFTRACE         = "262";
   public String code_TRYAGAIN           = "263";
   public String code_N_LOCAL            = "265";
   public String code_N_GLOBAL           = "266";
   public String code_SILELIST           = irc_draft == "Undernet" ? "271" : null;
   public String code_ENDOFSILELIST      = irc_draft == "Undernet" ? "272" : null;
   public String code_STATUSDLINE        = irc_draft == "Undernet" ? "275" : null;
   public String code_GLIST              = irc_draft == "Undernet" ? "280" : null;
   public String code_ENDOFGLIST         = irc_draft == "Undernet" ? "281" : null;
   public String code_HELPHDR            = irc_draft == "Unreal"   ? "290" : null;
   public String code_HELPOP             = irc_draft == "Unreal"   ? "291" : null;
   public String code_HELPTLR            = irc_draft == "Unreal"   ? "292" : null;
   public String code_HELPHLP            = irc_draft == "Unreal"   ? "293" : null;
   public String code_HELPFWD            = irc_draft == "Unreal"   ? "294" : null;
   public String code_HELPIGN            = irc_draft == "Unreal"   ? "295" : null;
   public String code_NONE               = "300";
   public String code_AWAY               = "301";
   public String code_USERHOST           = "302";
   public String code_ISON               = "303";
   public String code_RPL_TEXT           = irc_draft == "Bahamut"  ? "304" : null;
   public String code_UNAWAY             = "305";
   public String code_NOAWAY             = "306";
   public String code_USERIP             = irc_draft == "Undernet" ? "307" : null;
   public String code_RULESSTART         = irc_draft == "Unreal"   ? "308" : null;
   public String code_ENDOFRULES         = irc_draft == "Unreal"   ? "309" : null;
   public String code_WHOISHELP          = "310"; // Unknown
   public String code_WHOISUSER          = "311";
   public String code_WHOISSERVER        = "312";
   public String code_WHOISOPERATOR      = "313";
   public String code_WHOWASUSER         = "314";
   public String code_ENDOFWHO           = "315";
   public String code_WHOISCHANOP        = "316";
   public String code_WHOISIDLE          = "317";
   public String code_ENDOFWHOIS         = "318";
   public String code_WHOISCHANNELS      = "319";
   public String code_WHOISWORLD         = "320"; // Unknown
   public String code_LISTSTART          = "321";
   public String code_LIST               = "322";
   public String code_LISTEND            = "323";
   public String code_CHANNELMODEIS      = "324";
   public String code_CHANNELCREATE      = "329";
   public String code_NOTOPIC            = "331";
   public String code_CURRENTTOPIC       = "332";
   public String code_TOPICINFO          = "333";
   public String code_LISTUSAGE          = irc_draft == "Undernet" ? "334" : null;
   public String code_WHOISBOT           = irc_draft == "Unreal"   ? "335" : null;
   public String code_INVITING           = "341";
   public String code_SUMMONING          = "342";
   public String code_INVITELIST         = irc_draft == "Unreal"   ? "346" : null;
   public String code_ENDOFINVITELIST    = irc_draft == "Unreal"   ? "347" : null;
   public String code_EXCEPTLIST         = irc_draft == "Unreal"   ? "348" : null;
   public String code_ENDOFEXCEPTLIST    = irc_draft == "Unreal"   ? "349" : null;
   public String code_VERSION            = "351";
   public String code_WHOREPLY           = "352";
   public String code_NAMREPLY           = "353";
   public String code_WHOSPCRP1          = irc_draft == "Undernet" ? "354" : null;
   public String code_KILLDONE           = "361";
   public String code_CLOSING            = "362";
   public String code_CLOSEEND           = "363";
   public String code_LINKS              = "364";
   public String code_ENDOFLINKS         = "365";
   public String code_ENDOFNAMES         = "366";
   public String code_BANLIST            = "367";
   public String code_ENDOFBANLIST       = "368";
   public String code_ENDOFWHOWAS        = "369";
   public String code_INFO               = "371";
   public String code_MOTD               = "372";
   public String code_INFOSTART          = "373";
   public String code_ENDOFINFO          = "374";
   public String code_MOTDSTART          = "375";
   public String code_ENDOFMOTD          = "376";
   public String code_MOTD2              = "377"; // Unknown
   public String code_AUSTMOTD           = "378"; // Unknown
   public String code_WHOISMODES         = irc_draft == "Unreal"   ? "379" : null;
   public String code_YOUREOPER          = "381";
   public String code_REHASHING          = "382";
   public String code_YOURESERVICE       = irc_draft == "Unreal"   ? "383" : null;
   public String code_MYPORTIS           = "384";
   public String code_NOTOPERANYMORE     = "385"; // Unknown
   public String code_QLIST              = irc_draft == "Unreal"   ? "386" : null;
   public String code_ENDOFQLIST         = irc_draft == "Unreal"   ? "387" : null;
   public String code_ALIST              = irc_draft == "Unreal"   ? "388" : null;
   public String code_ENDOFALIST         = irc_draft == "Unreal"   ? "389" : null;
   public String code_TIME               = "391";
   public String code_USERSSTART         = "392";
   public String code_USERS              = "393";
   public String code_ENDOFUSERS         = "394";
   public String code_NOUSER             = "395";
   public String code_NOSUCHNICK         = "401";
   public String code_NOSUCHSERVER       = "402";
   public String code_NOSUCHCHANNEL      = "403";
   public String code_CANNOTSENDTOCHAN   = "404";
   public String code_TOOMANYCHANNELS    = "405";
   public String code_WASNOSUCHNICK      = "406";
   public String code_TOOMANYTARGETS     = "407";
   public String code_NOSUCHSERVICE      = irc_draft == "Unreal"   ? "408" : null;
   public String code_NOORIGIN           = "409";
   public String code_NORECIPIENT        = "411";
   public String code_NOTEXTTOSEND       = "412";
   public String code_NOOPLEVEL          = "413";
   public String code_WILDTOPLEVEL       = "414";
   public String code_QUERYTOOLONG       = irc_draft == "Undernet" ? "416" : null;
   public String code_UNKNOWNCOMMAND     = "421";
   public String code_NOMOTD             = "422";
   public String code_NOADMININFO        = "423";
   public String code_FILEERROR          = "424";
   public String code_NOOPERMOTD         = irc_draft == "Unreal"   ? "425" : null;
   public String code_NONICKNAMEGIVEN    = "431";
   public String code_ERRONEUSNICKNAME   = "432";
   public String code_NICKNAMEINUSE      = "433";
   public String code_NORULES            = irc_draft == "Unreal"   ? "434" : null;
   public String code_SERVICECONFUSED    = irc_draft == "Unreal"   ? "435" : null;
   public String code_NICKCOLLISION      = "436";
   public String code_UNAVAILRESOURCE    = irc_draft != "Undernet" ? "437" : null;
   public String code_BANNICKCHANGE      = irc_draft == "Undernet" ? "437" : null;
   public String code_NICKCHANGETOOFAST  = irc_draft == "Undernet" ? "438" : null;
   public String code_TARGETTOOFAST      = irc_draft == "Undernet" ? "439" : null;
   public String code_SERVICESDOWN       = irc_draft == "Bahamut"  ? "440" : null;
   public String code_USERNOTINCHANNEL   = "441";
   public String code_NOTONCHANNEL       = "442";
   public String code_USERONCHANNEL      = "443";
   public String code_NOLOGIN            = "444";
   public String code_SUMMONDISABLED     = "445";
   public String code_USERSDISABLED      = "446";
   public String code_NONICKCHANGE       = irc_draft == "Unreal"   ? "447" : null;
   public String code_NOTREGISTERED      = "451";
   public String code_HOSTILENAME        = irc_draft == "Unreal"   ? "455" : null;
   public String code_NOHIDING           = irc_draft == "Unreal"   ? "459" : null;
   public String code_NOTFORHALFOPS      = irc_draft == "Unreal"   ? "460" : null;
   public String code_NEEDMOREPARAMS     = "461";
   public String code_ALREADYREGISTERED  = "462";
   public String code_NOPERMFORHOST      = "463";
   public String code_PASSWDMISMATCH     = "464";
   public String code_YOUREBANNEDCREEP   = "465";
   public String code_YOUWILLBEBANNED    = "466";
   public String code_KEYSET             = "467";
   public String code_INVALIDUSERNAME    = irc_draft == "Undernet" ? "468" : null;
   public String code_LINKSET            = irc_draft == "Unreal"   ? "469" : null;
   public String code_LINKCHANNEL        = irc_draft == "Unreal"   ? "470" : null;
   public String code_CHANNELISFULL      = "471";
   public String code_UNKNOWNMODE        = "472";
   public String code_INVITEONLYCHAN     = "473";
   public String code_BANNEDFROMCHAN     = "474";
   public String code_BADCHANNELKEY      = "475";
   public String code_BADCHANNELMASK     = "476";
   public String code_NOCHANMODES        = irc_draft != "Bahamut"  ? "477" : null;
   public String code_NEEDREGGEDNICK     = irc_draft == "Bahamut"  ? "477" : null;
   public String code_BANLISTFULL        = "478";
   public String code_SECUREONLYCHANNEL  = irc_draft == "pircd"    ? "479" : null;
   public String code_LINKFULL           = irc_draft == "Unreal"   ? "479" : null;
   public String code_CANNOTKNOCK        = irc_draft == "Unreal"   ? "480" : null;
   public String code_NOPRIVILEGES       = "481";
   public String code_CHANOPRIVSNEEDED   = "482";
   public String code_CANTKILLSERVER     = "483";
   public String code_RESTRICTED         = irc_draft != "Undernet" ? "484" : null;
   public String code_ISCHANSERVICE      = irc_draft == "Undernet" ? "484" : null;
   public String code_UNIQOPPRIVSNEEDED  = irc_draft != "Unreal"   ? "485" : null;
   public String code_KILLDENY           = irc_draft == "Unreal"   ? "485" : null;
   public String code_HTMDISABLED        = irc_draft == "Unreal"   ? "486" : null;
   public String code_SECUREONLYCHAN     = irc_draft == "Unreal"   ? "489" : null;
   public String code_NOOPERHOST         = "491";
   public String code_NOSERVICEHOST      = "492";
   public String code_UMODEUNKNOWNFLAG   = "501";
   public String code_USERSDONTMATCH     = "502";
   public String code_SILELISTFULL       = irc_draft == "Undernet" ? "511" : null;
   public String code_TOOMANYWATCH       = "512"; // Unknown
   public String code_NOSUCHGLINE        = irc_draft == "Undernet" ? "513" : null;
   public String code_BADPING            = irc_draft != "Undernet" ? "513" : null;
   public String code_NOINVITE           = irc_draft == "Unreal"   ? "518" : null;
   public String code_ADMONLY            = irc_draft == "Unreal"   ? "519" : null;
   public String code_OPERONLY           = irc_draft == "Unreal"   ? "520" : null;
   public String code_LISTSYTAX          = irc_draft == "Unreal"   ? "521" : null;
   public String code_OPERSPVERIFY       = irc_draft == "Unreal"   ? "524" : null;
   public String code_RPL_LOGON          = irc_draft == "Unreal"   ? "600" : null;
   public String code_RPL_LOGOFF         = irc_draft == "Unreal"   ? "601" : null;
   public String code_RPL_WATCHOFF       = irc_draft == "Unreal"   ? "602" : null;
   public String code_RPL_WATCHSTAT      = irc_draft == "Unreal"   ? "603" : null;
   public String code_RPL_NOWON          = irc_draft == "Bahamut"  ? "604" : null;
   public String code_RPL_NOWOFF         = irc_draft == "Bahamut"  ? "605" : null;
   public String code_RPL_WATCHLIST      = irc_draft == "Unreal"   ? "606" : null;
   public String code_RPL_ENDOFWATCHLIST = irc_draft == "Unreal"   ? "607" : null;
   public String code_RPL_DUMPING        = irc_draft == "Unreal"   ? "640" : null;
   public String code_RPL_DUMPRPL        = irc_draft == "Unreal"   ? "641" : null;
   public String code_RPL_EODUMP         = irc_draft == "Unreal"   ? "642" : null;
   public String code_NUMERICERROR       = irc_draft == "Bahamut"  ? "999" : null;
   //
   public init_CONST() {
     if (this.irc_draft == "Undernet") {
       this.default_mtu = 450;
       this.code_STATSTLINE = "246";
       this.code_STATSGLINE = "247";
     };
     if (this.irc_draft == "Unreal") {
       this.code_STATSNLINE = "226";
       this.code_MAPMORE    = "610";
     };
   };
  };

  public final static init_CONST CONST = new init_CONST();

  public JLayerIRC() { // Class constructor
    //

  };

  public String irciot_protocol_version_() {
    return this.CONST.irciot_protocol_version;
  };

  public String irciot_library_version_() {
    return this.CONST.irciot_library_version;
  };

}
