package me.phantom.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.nanovg.NanoVG;

public class GlyphTextUtil {
   private static final int phd = 50000;
   public static final char[] ENCODE_TABLE = new char[]{
      '쑎',
      '쑏',
      '쎲',
      '쏒',
      '쐒',
      '쎆',
      '쏔',
      '쎊',
      '썴',
      '쎳',
      '썜',
      '쎄',
      '쏉',
      '썱',
      '썦',
      '썶',
      '쐏',
      '쎔',
      '쎍',
      '쐭',
      '쎌',
      '쏂',
      '쐠',
      '쏓',
      '쐍',
      '쎧',
      '쏻',
      '쏦',
      '쐙',
      '쏹',
      '썲',
      '썐',
      '썾',
      '썛',
      '쎖',
      '쐁',
      '쎏',
      '쐥',
      '쐦',
      '쏶',
      '쎅',
      '쎪',
      '쏮',
      '쎵',
      '썿',
      '쐗',
      '쏊',
      '쎛',
      '쏙',
      '쎮',
      '쎞',
      '쎱',
      '쎾',
      '쎃',
      '쏳',
      '썫',
      '쏃',
      '쑆',
      '쐑',
      '쎭',
      '쎇',
      '쐫',
      '쎂',
      '쐕',
      '썟',
      '쎶',
      '쐡',
      '쏾',
      '쎙',
      '쏪',
      '쏇',
      '쏀',
      '쐩',
      '쐾',
      '썖',
      '썭',
      '쎠',
      '쎒',
      '쐻',
      '썣',
      '쏯',
      '쏟',
      '쑅',
      '쐤',
      '쑋',
      '썚',
      '쑃',
      '쐚',
      '썸',
      '쎣',
      '썙',
      '쏚',
      '쏵',
      '쐰',
      '쎥',
      '쎴',
      '쎫',
      '쏈',
      '쑄',
      '썠',
      '쎋',
      '쏖',
      '썢',
      '쎑',
      '쐄',
      '쐟',
      '쎹',
      '쏁',
      '쑇',
      '쐸',
      '쐎',
      '쏥',
      '쎐',
      '썳',
      '쐮',
      '쐆',
      '쎼',
      '쐂',
      '쐱',
      '썕',
      '쐞',
      '썥',
      '쏱',
      '썽',
      '쐴',
      '쐈',
      '쏴',
      '쐧',
      '쎦',
      '써',
      '쎎',
      '쏆',
      '쏑',
      '쏠',
      '쐹',
      '쐅',
      '쎻',
      '쎤',
      '쎯',
      '쎺',
      '쎟',
      '쏧',
      '쏤',
      '쐜',
      '썮',
      '쐿',
      '쏘',
      '썼',
      '쐔',
      '쐵',
      '쏨',
      '쐊',
      '쑀',
      '썝',
      '쏸',
      '쎬',
      '쐪',
      '쎓',
      '쏝',
      '쐢',
      '쑂',
      '쏢',
      '쐬',
      '쏄',
      '쐘',
      '썧',
      '썷',
      '쎕',
      '쏜',
      '쎁',
      '쏗',
      '쏕',
      '쏣',
      '쐇',
      '쐳',
      '쐖',
      '쏭',
      '쐝',
      '쏐',
      '쎝',
      '쐺',
      '쑊',
      '쑉',
      '쎡',
      '쎘',
      '쏺',
      '쐽',
      '쏲',
      '쎩',
      '썑',
      '쐀',
      '쎰',
      '쎀',
      '쎽',
      '썻',
      '쏿',
      '쏏',
      '쎿',
      '썓',
      '쐛',
      '쐲',
      '쐼',
      '쎷',
      '썘',
      '쑌',
      '쏩',
      '썺',
      '썰',
      '썒',
      '쏌',
      '쐯',
      '쑈',
      '썪',
      '썡',
      '썩',
      '쎈',
      '쎉',
      '쐨',
      '쐉',
      '쏍',
      '쎗',
      '쎸',
      '쎜',
      '쐶',
      '썤',
      '썯',
      '쏰',
      '쐐',
      '쐓',
      '쏽',
      '썵',
      '쐌',
      '쏅',
      '쎚',
      '쎨',
      '쏋',
      '쏫',
      '쏛',
      '쐷',
      '썔',
      '썞',
      '쑍',
      '쏼',
      '쏡',
      '쏞',
      '썹',
      '쐣',
      '썗',
      '쏎',
      '쑁',
      '쏬',
      '쐃',
      '쎢',
      '쏷',
      '썬',
      '쐋'
   };
   public static final char[] DECODE_TABLE = new char[]{
      '\u001f',
      '½',
      'Ð',
      'Æ',
      'ï',
      'w',
      'J',
      '÷',
      'Ë',
      'Z',
      'U',
      '!',
      '\n',
      '\u0099',
      'ð',
      '@',
      'c',
      'Õ',
      'f',
      'O',
      'à',
      'y',
      '\u000e',
      '¥',
      '\u0081',
      'Ö',
      'Ô',
      '7',
      'þ',
      'K',
      '\u0090',
      'á',
      'Ï',
      '\r',
      '\u001e',
      'q',
      '\b',
      'æ',
      '\u000f',
      '¦',
      'X',
      'õ',
      'Î',
      'Â',
      '\u0093',
      '{',
      ' ',
      ',',
      'À',
      '©',
      '>',
      '5',
      '\u000b',
      '(',
      '\u0005',
      '<',
      '×',
      'Ø',
      '\u0007',
      'd',
      '\u0014',
      '\u0012',
      '\u0082',
      '$',
      'p',
      'g',
      'M',
      '\u009d',
      '\u0011',
      '§',
      '"',
      'Ü',
      '¸',
      'D',
      'é',
      '/',
      'Þ',
      '³',
      '2',
      '\u008c',
      'L',
      '·',
      'ü',
      'Y',
      '\u0089',
      '^',
      '\u0080',
      '\u0019',
      'ê',
      '¼',
      ')',
      '`',
      '\u009b',
      ';',
      '1',
      '\u008a',
      '¿',
      '3',
      '\u0002',
      '\t',
      '_',
      '+',
      'A',
      'Ê',
      'Ý',
      'j',
      '\u008b',
      '\u0088',
      't',
      'Á',
      '4',
      'Å',
      'G',
      'k',
      '\u0015',
      '8',
      '£',
      'è',
      '\u0083',
      'F',
      'a',
      '\f',
      '.',
      'ë',
      'Ñ',
      'Û',
      'ø',
      'Ä',
      '²',
      '\u0084',
      '\u0003',
      '\u0017',
      '\u0006',
      '«',
      'e',
      'ª',
      '\u0092',
      '0',
      '[',
      'í',
      '¨',
      '\u009e',
      'ô',
      'Q',
      '\u0085',
      'ó',
      '¡',
      '¬',
      '\u008e',
      'o',
      '\u001b',
      '\u008d',
      '\u0096',
      'Í',
      'E',
      'ì',
      'ú',
      '°',
      '*',
      'P',
      'â',
      'z',
      '»',
      '6',
      '~',
      '\\',
      '\'',
      'ý',
      '\u009a',
      '\u001d',
      '¹',
      '\u001a',
      'ò',
      'å',
      'C',
      'Ã',
      '¾',
      '#',
      'u',
      'û',
      'h',
      '\u0087',
      's',
      '\u00ad',
      '}',
      'Ú',
      '\u0097',
      'ÿ',
      'ç',
      '\u0018',
      'n',
      '\u0010',
      'ã',
      ':',
      '\u0004',
      'ä',
      '\u0094',
      '?',
      '¯',
      '-',
      '¤',
      '\u001c',
      'W',
      'Ç',
      '\u008f',
      '±',
      'x',
      'i',
      '\u0016',
      'B',
      '\u009f',
      'ö',
      'S',
      '%',
      '&',
      '\u007f',
      'Ù',
      'H',
      '\u009c',
      '=',
      '¢',
      '\u0013',
      'r',
      'Ò',
      ']',
      'v',
      'È',
      '®',
      '|',
      '\u0095',
      'ß',
      'î',
      'm',
      '\u0086',
      '´',
      'N',
      'É',
      'º',
      'I',
      '\u0091',
      '\u0098',
      'ù',
      ' ',
      'V',
      'b',
      'R',
      '9',
      'l',
      'Ó',
      '¶',
      'µ',
      'T',
      'Ì',
      'ñ',
      '\u0000',
      '\u0001'
   };
   public static final long jf = 0L;
   public static int currentTextAlign;

   public static void ck(String[] var0) throws Throwable {
       // debug print removed
   }

   public static char encodeChar(char var0) {
      return var0 < ENCODE_TABLE.length ? ENCODE_TABLE[var0] : var0;
   }

   public static char decodeChar(char var0) {
      return var0 >= '썐' && var0 < 50000 + DECODE_TABLE.length ? DECODE_TABLE[var0 - '썐'] : var0;
   }

   public static String encode(String var0) {
      char[] var1 = var0.toCharArray();

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = encodeChar(var1[var2]);
      }

      return new String(var1);
   }

   public static boolean encodedEquals(String var0, String var1) {
      if (var0.length() != var1.length()) {
         return false;
      } else {
         for (int var2 = 0; var2 < var0.length(); var2++) {
            if (encodeChar(var0.charAt(var2)) != encodeChar(var1.charAt(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public static String plain(String var0) {
      return var0;
   }

   public static void setTextAlign(long var0, int var2) {
      currentTextAlign = var2;
      NanoVG.nvgTextAlign(var0, var2);
   }

   public static float drawText(long var0, float var2, float var3, CharSequence var4) {
      if ((currentTextAlign & 2) != 0) {
         NanoVG.nvgTextAlign(var0, currentTextAlign & -3 | 1);
         var2 -= measureText(var0, var2, var3, var4, (FloatBuffer)null) / 2.0F;
      } else if ((currentTextAlign & 4) != 0) {
         NanoVG.nvgTextAlign(var0, currentTextAlign & -5 | 1);
         var2 -= measureText(var0, var2, var3, var4, (FloatBuffer)null);
      }

      for (int var5 = 0; var5 < var4.length(); var5++) {
         char var6 = var4.charAt(var5);
         var6 = decodeChar(var6);
         var2 = NanoVG.nvgText(var0, var2, var3, String.valueOf(var6));
      }

      if ((currentTextAlign & 6) != 0) {
         NanoVG.nvgTextAlign(var0, currentTextAlign);
      }

      return var2;
   }

   public static float drawTextBuffer(long var0, float var2, float var3, ByteBuffer var4) {
      return NanoVG.nvgText(var0, var2, var3, var4);
   }

   public static float measureText(long var0, float var2, float var3, CharSequence var4, FloatBuffer var5) {
      float var6 = 0.0F;

      for (int var7 = 0; var7 < var4.length(); var7++) {
         char var8 = var4.charAt(var7);
         var8 = decodeChar(var8);
         var6 += NanoVG.nvgTextBounds(var0, var2, var3, String.valueOf(var8), var5);
      }

      return var6;
   }

   public static String decode(String var0) {
      char[] var1 = var0.toCharArray();

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = decodeChar(var1[var2]);
      }

      return new String(var1);
   }

   public static String lowercase(String var0) {
      char[] var1 = var0.toCharArray();
      char[] var2 = new char[var1.length];

      for (int var3 = 0; var3 < var1.length; var3++) {
         var2[var3] = encodeChar(Character.toLowerCase(decodeChar(var1[var3])));
      }

      return new String(var2);
   }

   public static String uppercase(String var0) {
      char[] var1 = var0.toCharArray();
      char[] var2 = new char[var1.length];

      for (int var3 = 0; var3 < var1.length; var3++) {
         var2[var3] = encodeChar(Character.toUpperCase(decodeChar(var1[var3])));
      }

      return new String(var2);
   }

   public static String encodeChars(String var0) {
      char[] var1 = var0.toCharArray();

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = encodeChar(var1[var2]);
      }

      return new String(var1);
   }

   public static String trim(String var0) {
      char var1 = encodeChar(' ');

      while (var0.length() > 0 && var0.charAt(0) == var1) {
         var0 = var0.substring(1);
      }

      while (var0.length() > 0 && var0.charAt(var0.length() - 1) == var1) {
         var0 = var0.substring(0, var0.length() - 1);
      }

      return var0;
   }
}
