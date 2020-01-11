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
 
package javairciot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.OutputStreamWriter;
import java.io.IOException;
import org.javatuples.Triplet;
import org.javatuples.Quartet;

@SuppressWarnings("unchecked")

public class jlayerirc {

  // Those Global options override default behavior and memory usage:
  //
  public static final boolean DO_debug_library  = false;

  public static final class init_constants {
   //
   public String irciot_library_version = "0.0.167";
   //
   public String irciot_protocol_version = "0.3.29";
   //
   private static final long serialVersionUID = 32765;
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
   public boolean irc_default_ident = false;
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
   public String ipv4_pattern = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
    + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
   public String ipv6_pattern = "^((([0-9A-Fa-f]{1,4}+:){7}+[0-9A-Fa-f]{1,4}+)"
    + "|(:(:[0-9A-Fa-f]{1,4}+){1,6}+)|(([0-9A-Fa-f]{1,4}+:){1,6}+:)|(::)"
    + "|(([0-9A-Fa-f]{1,4}+:)(:[0-9A-Fa-f]{1,4}+){1,5}+)|(([0-9A-Fa-f]{1,4}+:){1,2}"
    + "+(:[0-9A-Fa-f]{1,4}+){1,4}+)|(([0-9A-Fa-f]{1,4}+:){1,3}+(:[0-9A-Fa-f]{1,4}+){1,3}"
    + "+)|(([0-9A-Fa-f]{1,4}+:){1,4}+(:[0-9A-Fa-f]{1,4}+){1,2}+)|(([0-9A-Fa-f]{1,4}+:){1,5}"
    + "+(:[0-9A-Fa-f]{1,4}+))|((([0-9A-Fa-f]{1,4}+:){1,4}+|:)(:(([0-9]{1,3}+\\.){3}"
    + "+[0-9]{1,3}+))))(/[0-9]+)?";
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
   public String irc_translation_in = irc_ascii_uppercase + "[]\\^";
   public String irc_translation_out = irc_ascii_lowercase + "{}|~";
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
   public String cmd_ACCOUNT    = irc_draft == "Undernet" ? "ACCOUNT"   : null;
   public String cmd_ADMIN      = "ADMIN";
   public String cmd_AWAY       = "AWAY";
   public String cmd_CLARMODE   = irc_draft == "Undernet" ? "CLEARMODE" : null;
   public String cmd_CLOSE      = irc_draft == "Undernet" ? "CLOSE"     : null;
   public String cmd_CNOTICE    = irc_draft == "Undernet" ? "CNOTICE"   : null;
   public String cmd_CONNECT    = irc_draft == "Undernet" ? "CONNECT"   : null;
   public String cmd_CPRIVMSG   = irc_draft == "Undernet" ? "CPRIVMSG"  : null;
   public String cmd_CREATE     = irc_draft == "Undernet" ? "CREATE"    : null;
   public String cmd_CTCP       = "CTCP";
   public String cmd_CTCPREPLY  = "CTCPREPLY";
   public String cmd_DESTRUCT   = irc_draft == "Undernet" ? "DESCTRUCT" : null;
   public String cmd_DESYNCH    = irc_draft == "Undernet" ? "DESYNCH"   : null;
   public String cmd_DCC_CON    = "DCC_CONNECT";
   public String cmd_DCC_DISCON = "DCC_DISCONNECT";
   public String cmd_DCC_MSG    = "DCCMSG";
   public String cmd_DIE        = irc_draft == "Undernet" ? "DIE"       : null;
   public String cmd_DISCONNECT = "DISCONNECT";
   public String cmd_ERROR      = "ERROR";
   public String cmd_GLINE      = irc_draft == "Undernet" ? "GLINE"     : null;
   public String cmd_HASH       = irc_draft == "Undernet" ? "HASH"      : null;
   public String cmd_HELP       = irc_draft == "Undernet" ? "HELP"      : null;
   public String cmd_INFO       = "INFO";
   public String cmd_INVITE     = "INVITE";
   public String cmd_ISON       = "ISON";
   public String cmd_JOIN       = "JOIN";
   public String cmd_JUPE       = irc_draft == "Undernet" ? "JUPE"      : null;
   public String cmd_KICK       = "KICK";
   public String cmd_KILL       = "KILL";
   public String cmd_LINKS      = "LINKS";
   public String cmd_LIST       = "LIST";
   public String cmd_LUSERS     = irc_draft == "Undernet" ? "LUSERS"    : null;
   public String cmd_MODE       = "MODE";
   public String cmd_MOTD       = "MOTD";
   public String cmd_NAMES      = "NAMES";
   public String cmd_MAP        = irc_draft == "Undernet" ? "MAP"       : null;
   public String cmd_NICK       = "NICK";
   public String cmd_NOTICE     = "NOTICE";
   public String cmd_NJOIN      = "NJOIN";
   public String cmd_OPER       = "OPER";
   public String cmd_OPMODE     = irc_draft == "Undernet" ? "OPMODE"    : null;
   public String cmd_PART       = "PART";
   public String cmd_PASS       = "PASS";
   public String cmd_PING       = "PING";
   public String cmd_PONG       = "PONG";
   public String cmd_PRIVMSG    = "PRIVMSG";
   public String cmd_PRIVS      = irc_draft == "Undernet" ? "PRIVS"     : null;
   public String cmd_PRIVNOTICE = "PRIVNOTICE";
   public String cmd_PROTO      = irc_draft == "Undernet" ? "PROTO"     : null;
   public String cmd_PUBMSG     = "PUBMSG";
   public String cmd_PUBNOTICE  = "PUBNOTICE";
   public String cmd_REHASH     = "REHASH";
   public String cmd_RESET      = irc_draft == "Undernet" ? "RESET"     : null;
   public String cmd_RESTART    = "RESTART";
   public String cmd_RPING      = irc_draft == "Undernet" ? "RPING"     : null;
   public String cmd_RPONG      = irc_draft == "Undernet" ? "RPONG"     : null;
   public String cmd_QUIT       = "QUIT";
   public String cmd_SERVER     = "SERVER";
   public String cmd_SET        = irc_draft == "Undernet" ? "SET"       : null;
   public String cmd_SETTIME    = irc_draft == "Undernet" ? "SETTIME"   : null;
   public String cmd_SILENCE    = irc_draft == "Undernet" ? "SILENCE"   : null;
   public String cmd_SQUIT      = "SQUIT";
   public String cmd_STATS      = "STATS";
   public String cmd_SUMMON     = "SUMMON";
   public String cmd_TIME       = "TIME";
   public String cmd_TOPIC      = "TOPIC";
   public String cmd_TRACE      = "TRACE";
   public String cmd_UPING      = irc_draft == "Undernet" ? "UPING"     : null;
   public String cmd_USER       = "USER";
   public String cmd_USERIP     = irc_draft == "Undernet" ? "USERIP"    : null;
   public String cmd_USERS      = "USERS";
   public String cmd_USERHOST   = "USERHOST";
   public String cmd_VERSION    = "VERSION";
   public String cmd_WALLCHOPS  = irc_draft == "Undernet" ? "WALLCHOPS" : null;
   public String cmd_WALLOPS    = "WALLOPS";
   public String cmd_WALLUSERS  = irc_draft == "Undernet" ? "WALLUSERS" : null;
   public String cmd_WALLVOICE  = irc_draft == "Undernet" ? "WALLVOICE" : null;
   public String cmd_WHOIS      = "WHOIS";
   public String cmd_WHOWAS     = "WHOWAS";
   public String cmd_WHO        = "WHO";
   //
   public String ident_default_ip   = "127.0.0.1";
   public int    ident_default_port = 113;
   //
   public init_constants() {
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

  public final static init_constants CONST = new init_constants();

  public boolean irc_run    = false;
  public boolean irc_debug  = CONST.irc_default_debug;
  public int     irc_init   = 0;
  public float   irc_wait   = 0;
  public boolean irc_ssl    = CONST.irc_default_ssl;
  public boolean irc_ident  = CONST.irc_default_ident;
  public Socket  irc        = null;
  public String  irc_server = null;
  public String  irc_nick   = null;
  //
  public String irc_nick_old  = null;
  public String irc_nick_base = null;
  //
  public HashMap<String, String> irc_codes = null;
  //
  public jlayerirc() { // Class constructor
    //
    this.irc_nick = CONST.irc_default_nick;
    //
    this.irc_run  = false;
    this.irc_init = 0;

  };

  public String irciot_protocol_version_() {
    return CONST.irciot_protocol_version;
  };

  public String irciot_library_version_() {
    return CONST.irciot_library_version;
  };

  // incomplete
  public String irc_tolower_(String in_input) {
    return in_input;
  };

  // incomplete
  public void irc_track_clear_anons_() {

  };

  // incomplete
  public void irc_track_clear_nicks_() {

  };

  // incomplete
  public void irc_track_delete_nick_(String in_nick) {

  };

  // incomplete
  public String irc_track_get_nick_by_vuid_(String in_vuid) {

    return (String) null;
  };

  public String[] irc_get_list_(Object in_input) {
    String[] my_output = new String[] {};
    if (in_input instanceof String) {
      my_output[0] = (String) in_input;
    } else if (in_input instanceof String[])
      return (String[]) in_input;
    return my_output;
  };

  public boolean is_pattern_(String in_string, String in_pattern) {
    if (in_string == null || in_string.isEmpty()) return false;
    boolean my_check = true;
    Pattern my_pattern = Pattern.compile(in_pattern);
    try {
      Matcher my_matcher = my_pattern.matcher(in_string);
      my_check = my_matcher.matches();
      if (!my_check) return false;
    } catch (PatternSyntaxException my_ex) { return false; };
    return my_check;
  };

  public boolean is_ipv4_address_(String in_ipv4_address) {
    return this.is_pattern_(in_ipv4_address, CONST.ipv4_pattern);
  };

  public boolean is_ipv6_address_(String in_ipv6_address) {
    return this.is_pattern_(in_ipv6_address, CONST.ipv6_pattern);
  };

  public boolean is_ip_address_(String in_ip_address) {
    if (this.is_ipv4_address_(in_ip_address)) return true;
    if (this.is_ipv6_address_(in_ip_address)) return true;
    return false;
  };

  public boolean is_irc_nick_(String in_nick) {
    String my_pattern = "^[" + CONST.irc_ascii_letters
     + "_\\^\\[\\]\\{\\}][" + CONST.irc_ascii_letters
     + CONST.irc_ascii_digits + "-_\\^\\[\\]\\{\\}]{1,12}$";
    return this.is_pattern_(in_nick, my_pattern);
  };

  public boolean is_irc_channel_(String in_channel) {
    String my_pattern = "^#[" + CONST.irc_ascii_letters
    + CONST.irc_ascii_digits + "-_\\^\\[\\]\\{\\}]{1,24}$";
    return this.is_pattern_(in_channel, my_pattern);
  };

  // incomplete
  public boolean irc_compare_nicks_(String ref_nick, String cmp_nick) {

    return false;
  };

  // incomplete
  public boolean irc_compare_channels_(String ref_channel, String cmp_channel) {

    return false;
  };

  // incomplete
  public boolean irc_check_mask_(String in_from, String in_mask) {

    return false;
  };

  // incomplete
  public void irc_define_nick_(String in_nick) {
    if (!this.is_irc_nick_(in_nick)) return;
    this.irc_nick = in_nick;
    this.irc_nick_old = in_nick;
    this.irc_nick_base = in_nick;
    if (this.irc_run)
      this.irc_send_(CONST.cmd_NICK + " " + in_nick);
  };

  // incomplete
  public int irc_random_nick_(String in_nick, boolean in_force) {
    if (!this.is_irc_nick_(in_nick)) return -1;

    return -1;
  };

  // incomplete
  public int irc_send_(String irc_out) {
    if (irc_out == null || irc_out.isEmpty()) return -1;
    try {
      OutputStreamWriter my_osw = new OutputStreamWriter(this.irc.getOutputStream(), "UTF-8");
      my_osw.write(irc_out, 0, irc_out.length());
      my_osw.flush();
    } catch (SocketException my_ex) {
      return -1;
    } catch (IOException my_ex) {
      return -1;
    };
    return 0;
  };

  // incomplete
  public Triplet<Integer, String, Integer> irc_recv_(float recv_timeout) {

    return Triplet.with( -1, "", 0);
  };

  // incomplete
  public Socket irc_socket_(String in_server_name) {
    try {
      InetAddress my_ip = InetAddress.getByName(in_server_name);
      String my_ip_str = my_ip.toString();
      if (this.is_ipv6_address_(my_ip_str)) {

      };
      Socket my_socket = new Socket();

    } catch (UnknownHostException my_ex) {
      return null;
    };

    return null;
  };

  public Triplet<Integer, Integer, Float> multi_function_(String in_func,
    String in_string, Triplet<Integer, Integer, Float> in_args) {
    int in_ret = in_args.getValue0();
    int in_init = in_args.getValue1();
    float in_wait = in_args.getValue2();
    switch (in_func) {
      case "NICKNAMEINUSE":
      case "ERRONEUSNICKNAME":
        if (this.irc_random_nick_(this.irc_nick_base, false) == 1)
          return Triplet.with(-1, 0, in_wait);
      break;
      case "NOTREGISTERED":
      break;
      case "BANNEDFROMCHAN":
      case "CHANNELISFULL":
      case "BADCHANNELKEY":
      break;
      case "NICKCHANGETOOFAST":
      break;
      case "NAMREPLY":
      break;
      case "WHOISUSER":
      break;
      case "ENDOFNAMES":
      break;
      case "WHOREPLY":
      break;
      case "NOSUCHNICK":
      break;
      case "BANNICKCHANGE":
      case "NONICKCHANGE":
        // restoring nick
      break;
      default: break;
    };
    return Triplet.with(in_ret, in_init, in_wait);
  };

  // incomplete
  public void init_rfc1459_() {
   init_constants C = jlayerirc.CONST;
   HashMap<String, String> T = new HashMap<String, String>();
   T.put(C.code_NICKNAMEINUSE,    "NICKNAMEINUSE");
   T.put(C.code_NOTREGISTERED,    "NOTREGISTERED");
   T.put(C.code_NAMREPLY,         "NAMREPLY");
   T.put(C.code_WHOISUSER,        "WHOISUSER");
   T.put(C.code_ENDOFNAMES,       "ENDOFNAMES");
   T.put(C.code_WHOREPLY,         "WHOREPLY");
   T.put(C.code_NOSUCHNICK,       "NOSUCHNICK");
   T.put(C.code_CHANNELISFULL,    "CHANNELISFULL");
   T.put(C.code_BADCHANNELKEY,    "BADCHANNELKEY");
   T.put(C.code_ERRONEUSNICKNAME, "ERRONEUSNICKNAME");
   T.put(C.code_NOSUCHCHANNEL,    "NOSUCHCHANNEL");
   T.put(C.code_NOSUCHSERVER,     "NOSUCHSERVER");
   T.put(C.code_CANNOTSENDTOCHAN, "CANNOTSENDTOCHAN");
   T.put(C.code_TOOMANYCHANNELS,  "TOOMANYCHANNELS");
   T.put(C.code_WASNOSUCHNICK,    "WASNOSUCHNICK");
   T.put(C.code_TOOMANYTARGETS,   "TOOMANYTARGETS");
   T.put(C.code_NOORIGIN,         "NOORIGIN");
   T.put(C.code_NORECIPIENT,      "NORECIPIENT");
   T.put(C.code_NOTEXTTOSEND,     "NOTEXTTOSEND");
   T.put(C.code_NOOPLEVEL,        "NOOPLEVEL");
   T.put(C.code_WILDTOPLEVEL,     "WILDTOPLEVEL");
   T.put(C.code_UNKNOWNCOMMAND,   "UNKNOWNCOMMAND");
   T.put(C.code_NOMOTD,           "NOMOTD");
   T.put(C.code_NOADMININFO,      "NOADMININFO");
   T.put(C.code_FILEERROR,        "FILEERROR");
   T.put(C.code_NONICKNAMEGIVEN,  "NONICKNAMEGIVEN");
   T.put(C.code_NICKCOLLISION,    "NICKCOLLISION");
   T.put(C.code_UNAVAILRESOURCE,  "UNAVAILRESOURCE");
   T.put(C.code_USERNOTINCHANNEL, "USERNOTINCHANNEL");
   T.put(C.code_NOTONCHANNEL,     "NOTONCHANNEL");
   T.put(C.code_NOLOGIN,          "NOLOGIN");
   T.put(C.code_SUMMONDISABLED,   "SUMMONDISABLED");
   T.put(C.code_USERSDISABLED,    "USERSDISABLED");
   T.put(C.code_NEEDMOREPARAMS,   "NEEDMOREPARAMS");
   T.put(C.code_USERSDONTMATCH,   "USERSDONTMATCH");
   T.put(C.code_ALREADYREGISTERED,"ALREADYREGISTERED");
   T.put(C.code_PASSWDMISMATCH,   "PASSWDMISMATCH");
   T.put(C.code_YOUREBANNEDCREEP, "YOUREBANNEDCREEP");
   T.put(C.code_YOUWILLBEBANNED,  "YOUWILLBEBANNED");
   T.put(C.code_KEYSET,           "KEYSET");
   T.put(C.code_UNKNOWNMODE,      "UNKNOWNMODE");
   T.put(C.code_INVITEONLYCHAN,   "INVITEONLYCHAN");
   T.put(C.code_BADCHANNELMASK,   "BADCHANNELMASK");
   //
   if (CONST.irc_draft == "Undernet") {
     T.put(C.code_BANNICKCHANGE,  "BANNICKCHANGE");
     T.put(C.code_USERIP,         "USERIP");
     T.put(C.code_INVALIDUSERNAME,"INVALIDUSERNAME");
   } else if (CONST.irc_draft == "Unreal") {
     T.put(C.code_NONICKCHANGE,   "NONICKCHANGE");
     T.put(C.code_WHOISBOT,       "WHOISBOT");
     T.put(C.code_NOSUCHSERVICE,  "NOSUCHSERVICE");
     T.put(C.code_NOINVITE,       "NOINVITE");
   } else { // Unknown extending
     T.put(C.code_NOCHANMODES,    "NOCHANMODES");
     T.put(C.code_RESTRICTED,     "RESTRICTED");
   };
   //
   this.irc_codes = T;
   //

  };

  // incomplete
  public void irc_process_() {
    this.init_rfc1459_();
    // try {

      this.irc = this.irc_socket_(this.irc_server);

    // } catch (SocketException my_ex) {

    // };
    while (this.irc_run) {
      // try {


      // } catch (SocketException my_ex) {

      // } finally {

      // };
    }; // while irc_run

  };


}

