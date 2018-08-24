//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

interface DeflaterConstants {
    boolean DEBUGGING = false;
    int STORED_BLOCK = 0;
    int STATIC_TREES = 1;
    int DYN_TREES = 2;
    int PRESET_DICT = 32;
    int DEFAULT_MEM_LEVEL = 8;
    int MAX_MATCH = 258;
    int MIN_MATCH = 3;
    int MAX_WBITS = 15;
    int WSIZE = 32768;
    int WMASK = 32767;
    int HASH_BITS = 15;
    int HASH_SIZE = 32768;
    int HASH_MASK = 32767;
    int HASH_SHIFT = 5;
    int MIN_LOOKAHEAD = 262;
    int MAX_DIST = 32506;
    int PENDING_BUF_SIZE = 65536;
    int MAX_BLOCK_SIZE = Math.min(65535, 65531);
    int DEFLATE_STORED = 0;
    int DEFLATE_FAST = 1;
    int DEFLATE_SLOW = 2;
    int[] GOOD_LENGTH = new int[]{0, 4, 4, 4, 4, 8, 8, 8, 32, 32};
    int[] MAX_LAZY = new int[]{0, 4, 5, 6, 4, 16, 16, 32, 128, 258};
    int[] NICE_LENGTH = new int[]{0, 8, 16, 32, 16, 32, 128, 128, 258, 258};
    int[] MAX_CHAIN = new int[]{0, 4, 8, 32, 16, 32, 128, 256, 1024, 4096};
    int[] COMPR_FUNC = new int[]{0, 1, 1, 1, 1, 2, 2, 2, 2, 2};
}
