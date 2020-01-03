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

// Those Global options override default behavior and memory usage:
//

class JLayerIRC {

  public static final boolean DO_debug_library  = false;

  public static class init_CONST {
   //
   String irciot_library_version = "0.0.160";
   //
   String irciot_protocol_version = "0.3.29";
   //
   boolean irc_default_debug = DO_debug_library;
   //
   String irc_default_nick = "MyBot";
   String irc_default_info = "IRC-IoT Bot";
   String irc_default_quit = "Bye!";
   //
   String irc_default_server = "irc-iot.nsk.ru";
   int    irc_default_port  = 6667;
   String irc_default_password = null;
   boolean irc_default_ssl  = false;
   //
   String irc_default_proxy = null;
   String irc_default_proxy_server = null;
   int    irc_default_proxy_port = 0; // 0 -- no port
   String irc_default_proxy_password = null;
   //
   String irc_default_channel = "#myhome";
   String irc_default_chankey = null;
   //
   // User options:
   int irc_aop   = 101; // give (+o) him channel operator status
   int irc_aban  = 102; // ban (+b) him on these channels
   int irc_avo   = 103; // give (+v) him a voice on channels
   int irc_akick = 130; // kick it from these channels
   int irc_adeop = 131; // take away (-o) his channel operator status
   int irc_unban = 132; // unban (-b) mask on channels when banned
   int irc_adevo = 133; // take away (-v) his voice on channels
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
   int irc_default_mid_pipeline_size = 16;
   //
   boolean irc_default_talk_with_strangers = false;
   int irc_first_temporal_vuid = 1000;
   //
   int api_GET_LMID = 101; // Get last Message ID
   int api_SET_LMID = 102; // Set last Message ID
   int api_GET_OMID = 111; // Get Own last Message ID
   int api_SET_OMID = 112; // Set Own last Message ID
   int api_GET_EKEY = 301; // Get Encryption Key
   int api_SET_EKEY = 302; // Set Encryption Key
   int api_GET_EKTO = 351; // Get Encryption Key Timeout
   int api_SET_EKTO = 352; // Set Encyrption Key Timeout
   int api_GET_BKEY = 501; // Get Blockchain key
   int api_SET_BKEY = 502; // Set Blockchain Key
   int api_GET_BKTO = 551; // Get Blockchain Key Timeout
   int api_SET_BKTO = 552; // Set Blockchain Key Timeout
   int api_GET_VUID = 700; // Get list of Virutal User IDs
   //
   String api_vuid_cfg = "c"; // VUID prefix for users from config
   String api_vuid_tmp = "t"; // VUID prefix for temporal users
   String api_vuid_srv = "s"; // VUID prefix for IRC-IoT Services
   String api_vuid_all = "*"; // Means All users VUIDs when sending messages
   //
   String api_vuid_self = "c0"; // Default preconfigured VUID
   //
   // Basic IRC-IoT Services
   //
   String api_vuid_CRS = "sC"; // Сryptographic Repository Service
   String api_vuid_GDS = "sD"; // Global Dictionary Service
   String api_vuid_GRS = "sR"; // Global Resolving Service
   String api_vuid_GTS = "sT"; // Global Time Service
   //
   String api_vuid_RoS = "sr"; // Primary Routing Service
   //
   int irc_queue_input  = 0;
   int irc_queue_output = 1;
   //
   int irc_recon_steps  = 8;
   //
   int irc_buffer_size  = 2048;
   //
   String[] irc_layer_modes = { "CLIENT", "SERVICE", "SERVER" };
   //
   int irc_default_nick_retry = 3600; // in seconds
   //
   // According RFC 1459
   //
   String irc_ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
   String irc_ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   String irc_ascii_letters = irc_ascii_lowercase + irc_ascii_uppercase;
   String irc_ascii_digits = "0123456789";
   String irc_special_chars = "-[]\\`^{}";
   String irc_nick_first_char = irc_ascii_letters + irc_special_chars;
   String irc_nick_chars = irc_ascii_letters + irc_ascii_digits + irc_special_chars;
   String irc_mode_add = "+";
   String irc_mode_del = "-";
   String irc_change_modes = irc_mode_add + irc_mode_del;
   String irc_umode_op = "o";
   String irc_umode_voice = "v";
   String irc_umode_ban = "b";
   String irc_user_modes = irc_umode_op + irc_umode_voice + irc_umode_ban;
   String irc_channel_modes = "psitnm";
   String irc_extra_modes = "lk";
   String irc_all_modes = irc_user_modes + irc_channel_modes + irc_extra_modes;
   String irc_all_modes_chars = irc_change_modes + irc_all_modes;
   //
   int irc_max_nick_length = 15;
   //
   String irc_draft = "Undernet";
   //
   // "RFC1459", "Undernet", "Unreal", "Bahamut",   "Inspl", "Hybrid",
   // "RusNet",  "Shadow",   "ircu",   "Nefarious", "Rock",  "Synchronet",
   // "solid",   "PieXus",   "ratbox", "Charybdis", "pure",  "Rubl",
   // "ngl",     "ConfRoom", "pircd"
   //
   int default_mtu = 480;
   //
   String code_WELCOME            = "001";
   String code_YOURHOST           = "002";
   String code_CREATED            = "003";
   String code_MYINFO             = "004";
   String code_FEATURELIST        = "005"; // Unknown
   String code_MAP                = irc_draft == "Undernet" ? "005" : null;
   String code_MAPMORE            = irc_draft == "Undernet" ? "006" : null;
   String code_MAPEND             = irc_draft == "Undernet" ? "007" : null;
   String code_SNOMASK            = irc_draft == "Undernet" ? "008" : null;
   String code_STATMEMTOT         = irc_draft == "Undernet" ? "009" : null;
   String code_STATMEM            = irc_draft == "Undernet" ? "010" : null;
   String code_TRACELINK          = "200";
   String code_TRACECONNECTING    = "201";
   String code_TRACEHANDSHAKE     = "202";
   String code_TRACEUNKNOWN       = "203";
   String code_TRACEOPERATOR      = "204";
   String code_TRACEUSER          = "205";
   String code_TRACESERVER        = "206";
   String code_TRACESERVICE       = "207";
   String code_TRACENEWTYPE       = "208";
   String code_TRACECLASS         = "209";
   String code_TRACERECONNECT     = "210";
   String code_STATSLINKINFO      = "211";
   String code_STATSCOMMANDS      = "212";
   String code_STATSCLINE         = "213";
   String code_STATSNLINE         = "214";
   String code_STATSILINE         = "215";
   String code_STATSKLINE         = "216";
   String code_STATSQLINE         = "217";
   String code_STATSYLINE         = "218";
   String code_ENDOFSTATS         = "219";
   String code_STATSBLINE         = irc_draft == "Unreal"   ? "220" : null;
   String code_UMODEIS            = "221";
   String code_SQLINE_NICK        = irc_draft == "Unreal"   ? "222" : null;
   String code_STATSGLINE         = irc_draft == "Unreal"   ? "223" : null;
   String code_STATSTLINE         = irc_draft == "Unreal"   ? "224" : null;
   String code_STATSELINE         = irc_draft == "Unreal"   ? "225" : null;
   String code_STATSVLINE         = irc_draft == "Unreal"   ? "227" : null;
   String code_SERVICEINFO        = "231";
   String code_ENDOFSERVICES      = "232";
   String code_SERVICE            = "233";
   String code_SERVLIST           = "234";
   String code_SERVLISTEND        = "235";
   String code_STATSLLINE         = "241";
   String code_STATSUPTIME        = "242";
   String code_STATSOLINE         = "243";
   String code_STATSHLINE         = "244";
   String code_STATSSLINE         = "245"; // Unknown
   String code_STATSXLINE         = irc_draft == "Unreal"   ? "247" : null;
   String code_STATSULINE         = irc_draft == "Undernet" ? "248" : null;
   String code_STATSDEBUG         = "249"; // Unknown
   String code_LUSERCONNS         = "250";
   String code_LUSERCLIENT        = "251";
   String code_LUSEROP            = "252";
   String code_LUSERUNKNOWN       = "253";
   String code_LUSERCHANNELS      = "254";
   String code_LUSERME            = "255";
   String code_ADMINME            = "256";
   String code_ADMINLOC1          = "257";
   String code_ADMINLOC2          = "258";
   String code_ADMINEMAIL         = "259";
   String code_TRACELOG           = "261";
   String code_ENDOFTRACE         = "262";
   String code_TRYAGAIN           = "263";
   String code_N_LOCAL            = "265";
   String code_N_GLOBAL           = "266";
   String code_SILELIST           = irc_draft == "Undernet" ? "271" : null;
   String code_ENDOFSILELIST      = irc_draft == "Undernet" ? "272" : null;
   String code_STATUSDLINE        = irc_draft == "Undernet" ? "275" : null;
   String code_GLIST              = irc_draft == "Undernet" ? "280" : null;
   String code_ENDOFGLIST         = irc_draft == "Undernet" ? "281" : null;
   String code_HELPHDR            = irc_draft == "Unreal"   ? "290" : null;
   String code_HELPOP             = irc_draft == "Unreal"   ? "291" : null;
   String code_HELPTLR            = irc_draft == "Unreal"   ? "292" : null;
   String code_HELPHLP            = irc_draft == "Unreal"   ? "293" : null;
   String code_HELPFWD            = irc_draft == "Unreal"   ? "294" : null;
   String code_HELPIGN            = irc_draft == "Unreal"   ? "295" : null;
   String code_NONE               = "300";
   String code_AWAY               = "301";
   String code_USERHOST           = "302";
   String code_ISON               = "303";
   String code_RPL_TEXT           = irc_draft == "Bahamut"  ? "304" : null;
   String code_UNAWAY             = "305";
   String code_NOAWAY             = "306";
   String code_USERIP             = irc_draft == "Undernet" ? "307" : null;
   String code_RULESSTART         = irc_draft == "Unreal"   ? "308" : null;
   String code_ENDOFRULES         = irc_draft == "Unreal"   ? "309" : null;
   String code_WHOISHELP          = "310"; // Unknown
   String code_WHOISUSER          = "311";
   String code_WHOISSERVER        = "312";
   String code_WHOISOPERATOR      = "313";
   String code_WHOWASUSER         = "314";
   String code_ENDOFWHO           = "315";
   String code_WHOISCHANOP        = "316";
   String code_WHOISIDLE          = "317";
   String code_ENDOFWHOIS         = "318";
   String code_WHOISCHANNELS      = "319";
   String code_WHOISWORLD         = "320"; // Unknown
   String code_LISTSTART          = "321";
   String code_LIST               = "322";
   String code_LISTEND            = "323";
   String code_CHANNELMODEIS      = "324";
   String code_CHANNELCREATE      = "329";
   String code_NOTOPIC            = "331";
   String code_CURRENTTOPIC       = "332";
   String code_TOPICINFO          = "333";
   String code_LISTUSAGE          = irc_draft == "Undernet" ? "334" : null;
   String code_WHOISBOT           = irc_draft == "Unreal"   ? "335" : null;
   String code_INVITING           = "341";
   String code_SUMMONING          = "342";
   String code_INVITELIST         = irc_draft == "Unreal"   ? "346" : null;
   String code_ENDOFINVITELIST    = irc_draft == "Unreal"   ? "347" : null;
   String code_EXCEPTLIST         = irc_draft == "Unreal"   ? "348" : null;
   String code_ENDOFEXCEPTLIST    = irc_draft == "Unreal"   ? "349" : null;
   String code_VERSION            = "351";
   String code_WHOREPLY           = "352";
   String code_NAMREPLY           = "353";
   String code_WHOSPCRP1          = irc_draft == "Undernet" ? "354" : null;
   String code_KILLDONE           = "361";
   String code_CLOSING            = "362";
   String code_CLOSEEND           = "363";
   String code_LINKS              = "364";
   String code_ENDOFLINKS         = "365";
   String code_ENDOFNAMES         = "366";
   String code_BANLIST            = "367";
   String code_ENDOFBANLIST       = "368";
   String code_ENDOFWHOWAS        = "369";
   String code_INFO               = "371";
   String code_MOTD               = "372";
   String code_INFOSTART          = "373";
   String code_ENDOFINFO          = "374";
   String code_MOTDSTART          = "375";
   String code_ENDOFMOTD          = "376";
   String code_MOTD2              = "377"; // Unknown
   String code_AUSTMOTD           = "378"; // Unknown
   String code_WHOISMODES         = irc_draft == "Unreal"   ? "379" : null;
   String code_YOUREOPER          = "381";
   String code_REHASHING          = "382";
   String code_YOURESERVICE       = irc_draft == "Unreal"   ? "383" : null;
   String code_MYPORTIS           = "384";
   String code_NOTOPERANYMORE     = "385"; // Unknown
   String code_QLIST              = irc_draft == "Unreal"   ? "386" : null;
   String code_ENDOFQLIST         = irc_draft == "Unreal"   ? "387" : null;
   String code_ALIST              = irc_draft == "Unreal"   ? "388" : null;
   String code_ENDOFALIST         = irc_draft == "Unreal"   ? "389" : null;
   String code_TIME               = "391";
   String code_USERSSTART         = "392";
   String code_USERS              = "393";
   String code_ENDOFUSERS         = "394";
   String code_NOUSER             = "395";
   String code_NOSUCHNICK         = "401";
   String code_NOSUCHSERVER       = "402";
   String code_NOSUCHCHANNEL      = "403";
   String code_CANNOTSENDTOCHAN   = "404";
   String code_TOOMANYCHANNELS    = "405";
   String code_WASNOSUCHNICK      = "406";
   String code_TOOMANYTARGETS     = "407";
   String code_NOSUCHSERVICE      = irc_draft == "Unreal"   ? "408" : null;
   String code_NOORIGIN           = "409";
   String code_NORECIPIENT        = "411";
   String code_NOTEXTTOSEND       = "412";
   String code_NOOPLEVEL          = "413";
   String code_WILDTOPLEVEL       = "414";
   String code_QUERYTOOLONG       = irc_draft == "Undernet" ? "416" : null;
   String code_UNKNOWNCOMMAND     = "421";
   String code_NOMOTD             = "422";
   String code_NOADMININFO        = "423";
   String code_FILEERROR          = "424";
   String code_NOOPERMOTD         = irc_draft == "Unreal"   ? "425" : null;
   String code_NONICKNAMEGIVEN    = "431";
   String code_ERRONEUSNICKNAME   = "432";
   String code_NICKNAMEINUSE      = "433";
   String code_NORULES            = irc_draft == "Unreal"   ? "434" : null;
   String code_SERVICECONFUSED    = irc_draft == "Unreal"   ? "435" : null;
   String code_NICKCOLLISION      = "436";
   String code_UNAVAILRESOURCE    = irc_draft != "Undernet" ? "437" : null;
   String code_BANNICKCHANGE      = irc_draft == "Undernet" ? "437" : null;
   String code_NICKCHANGETOOFAST  = irc_draft == "Undernet" ? "438" : null;
   String code_TARGETTOOFAST      = irc_draft == "Undernet" ? "439" : null;
   String code_SERVICESDOWN       = irc_draft == "Bahamut"  ? "440" : null;
   String code_USERNOTINCHANNEL   = "441";
   String code_NOTONCHANNEL       = "442";
   String code_USERONCHANNEL      = "443";
   String code_NOLOGIN            = "444";
   String code_SUMMONDISABLED     = "445";
   String code_USERSDISABLED      = "446";
   String code_NONICKCHANGE       = irc_draft == "Unreal"   ? "447" : null;
   String code_NOTREGISTERED      = "451";
   String code_HOSTILENAME        = irc_draft == "Unreal"   ? "455" : null;
   String code_NOHIDING           = irc_draft == "Unreal"   ? "459" : null;
   String code_NOTFORHALFOPS      = irc_draft == "Unreal"   ? "460" : null;
   String code_NEEDMOREPARAMS     = "461";
   String code_ALREADYREGISTERED  = "462";
   String code_NOPERMFORHOST      = "463";
   String code_PASSWDMISMATCH     = "464";
   String code_YOUREBANNEDCREEP   = "465";
   String code_YOUWILLBEBANNED    = "466";
   String code_KEYSET             = "467";
   String code_INVALIDUSERNAME    = irc_draft == "Undernet" ? "468" : null;
   String code_LINKSET            = irc_draft == "Unreal"   ? "469" : null;
   String code_LINKCHANNEL        = irc_draft == "Unreal"   ? "470" : null;
   String code_CHANNELISFULL      = "471";
   String code_UNKNOWNMODE        = "472";
   String code_INVITEONLYCHAN     = "473";
   String code_BANNEDFROMCHAN     = "474";
   String code_BADCHANNELKEY      = "475";
   String code_BADCHANNELMASK     = "476";
   String code_NOCHANMODES        = irc_draft != "Bahamut"  ? "477" : null;
   String code_NEEDREGGEDNICK     = irc_draft == "Bahamut"  ? "477" : null;
   String code_BANLISTFULL        = "478";
   String code_SECUREONLYCHANNEL  = irc_draft == "pircd"    ? "479" : null;
   String code_LINKFULL           = irc_draft == "Unreal"   ? "479" : null;
   String code_CANNOTKNOCK        = irc_draft == "Unreal"   ? "480" : null;
   String code_NOPRIVILEGES       = "481";
   String code_CHANOPRIVSNEEDED   = "482";
   String code_CANTKILLSERVER     = "483";
   String code_RESTRICTED         = irc_draft != "Undernet" ? "484" : null;
   String code_ISCHANSERVICE      = irc_draft == "Undernet" ? "484" : null;
   String code_UNIQOPPRIVSNEEDED  = irc_draft != "Unreal"   ? "485" : null;
   String code_KILLDENY           = irc_draft == "Unreal"   ? "485" : null;
   String code_HTMDISABLED        = irc_draft == "Unreal"   ? "486" : null;
   String code_SECUREONLYCHAN     = irc_draft == "Unreal"   ? "489" : null;
   String code_NOOPERHOST         = "491";
   String code_NOSERVICEHOST      = "492";
   String code_UMODEUNKNOWNFLAG   = "501";
   String code_USERSDONTMATCH     = "502";
   String code_SILELISTFULL       = irc_draft == "Undernet" ? "511" : null;
   String code_TOOMANYWATCH       = "512"; // Unknown
   String code_NOSUCHGLINE        = irc_draft == "Undernet" ? "513" : null;
   String code_BADPING            = irc_draft != "Undernet" ? "513" : null;
   String code_NOINVITE           = irc_draft == "Unreal"   ? "518" : null;
   String code_ADMONLY            = irc_draft == "Unreal"   ? "519" : null;
   String code_OPERONLY           = irc_draft == "Unreal"   ? "520" : null;
   String code_LISTSYTAX          = irc_draft == "Unreal"   ? "521" : null;
   String code_OPERSPVERIFY       = irc_draft == "Unreal"   ? "524" : null;
   String code_RPL_LOGON          = irc_draft == "Unreal"   ? "600" : null;
   String code_RPL_LOGOFF         = irc_draft == "Unreal"   ? "601" : null;
   String code_RPL_WATCHOFF       = irc_draft == "Unreal"   ? "602" : null;
   String code_RPL_WATCHSTAT      = irc_draft == "Unreal"   ? "603" : null;
   String code_RPL_NOWON          = irc_draft == "Bahamut"  ? "604" : null;
   String code_RPL_NOWOFF         = irc_draft == "Bahamut"  ? "605" : null;
   String code_RPL_WATCHLIST      = irc_draft == "Unreal"   ? "606" : null;
   String code_RPL_ENDOFWATCHLIST = irc_draft == "Unreal"   ? "607" : null;
   String code_RPL_DUMPING        = irc_draft == "Unreal"   ? "640" : null;
   String code_RPL_DUMPRPL        = irc_draft == "Unreal"   ? "641" : null;
   String code_RPL_EODUMP         = irc_draft == "Unreal"   ? "642" : null;
   String code_NUMERICERROR       = irc_draft == "Bahamut"  ? "999" : null;
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
  
  public static init_CONST CONST = new init_CONST();
  
}
