//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

public class Inflater {
    private static final int[] CPLENS = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258};
    private static final int[] CPLEXT = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0};
    private static final int[] CPDIST = new int[]{1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577};
    private static final int[] CPDEXT = new int[]{0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13};
    private static final int DECODE_HEADER = 0;
    private static final int DECODE_DICT = 1;
    private static final int DECODE_BLOCKS = 2;
    private static final int DECODE_STORED_LEN1 = 3;
    private static final int DECODE_STORED_LEN2 = 4;
    private static final int DECODE_STORED = 5;
    private static final int DECODE_DYN_HEADER = 6;
    private static final int DECODE_HUFFMAN = 7;
    private static final int DECODE_HUFFMAN_LENBITS = 8;
    private static final int DECODE_HUFFMAN_DIST = 9;
    private static final int DECODE_HUFFMAN_DISTBITS = 10;
    private static final int DECODE_CHKSUM = 11;
    private static final int FINISHED = 12;
    private int mode;
    private int readAdler;
    private int neededBits;
    private int repLength;
    private int repDist;
    private int uncomprLen;
    private boolean isLastBlock;
    private int totalOut;
    private int totalIn;
    private boolean nowrap;
    private StreamManipulator input;
    private OutputWindow outputWindow;
    private InflaterDynHeader dynHeader;
    private InflaterHuffmanTree litlenTree;
    private InflaterHuffmanTree distTree;
    private Adler32 adler;

    public Inflater() {
        this(false);
    }

    public Inflater(boolean var1) {
        this.nowrap = var1;
        this.adler = new Adler32();
        this.input = new StreamManipulator();
        this.outputWindow = new OutputWindow();
        this.mode = var1 ? 2 : 0;
    }

    public void reset() {
        this.mode = this.nowrap ? 2 : 0;
        this.totalIn = this.totalOut = 0;
        this.input.reset();
        this.outputWindow.reset();
        this.dynHeader = null;
        this.litlenTree = null;
        this.distTree = null;
        this.isLastBlock = false;
        this.adler.reset();
    }

    private boolean decodeHeader() throws DataFormatException {
        int var1 = this.input.peekBits(16);
        if (var1 < 0) {
            return false;
        } else {
            this.input.dropBits(16);
            var1 = (var1 << 8 | var1 >> 8) & '\uffff';
            if (var1 % 31 != 0) {
                throw new DataFormatException("Header checksum illegal");
            } else if ((var1 & 3840) != 2048) {
                throw new DataFormatException("Compression Method unknown");
            } else {
                if ((var1 & 32) == 0) {
                    this.mode = 2;
                } else {
                    this.mode = 1;
                    this.neededBits = 32;
                }

                return true;
            }
        }
    }

    private boolean decodeDict() {
        while(this.neededBits > 0) {
            int var1 = this.input.peekBits(8);
            if (var1 < 0) {
                return false;
            }

            this.input.dropBits(8);
            this.readAdler = this.readAdler << 8 | var1;
            this.neededBits -= 8;
        }

        return false;
    }

    private boolean decodeHuffman() throws DataFormatException {
        for(int var1 = this.outputWindow.getFreeSpace(); var1 >= 258; this.mode = 7) {
            int var3;
            label85: {
                int var2;
                label91: {
                    switch(this.mode) {
                        case 7:
                            while(true) {
                                if (((var2 = this.litlenTree.getSymbol(this.input)) & -256) != 0) {
                                    if (var2 < 257) {
                                        if (var2 < 0) {
                                            return false;
                                        }

                                        this.distTree = null;
                                        this.litlenTree = null;
                                        this.mode = 2;
                                        return true;
                                    }

                                    try {
                                        this.repLength = CPLENS[var2 - 257];
                                        this.neededBits = CPLEXT[var2 - 257];
                                        break;
                                    } catch (ArrayIndexOutOfBoundsException var5) {
                                        throw new DataFormatException("Illegal rep length code");
                                    }
                                }

                                this.outputWindow.write(var2);
                                --var1;
                                if (var1 < 258) {
                                    return true;
                                }
                            }
                        case 8:
                            break;
                        case 9:
                            break label91;
                        case 10:
                            break label85;
                        default:
                            throw new IllegalStateException();
                    }

                    if (this.neededBits > 0) {
                        this.mode = 8;
                        var3 = this.input.peekBits(this.neededBits);
                        if (var3 < 0) {
                            return false;
                        }

                        this.input.dropBits(this.neededBits);
                        this.repLength += var3;
                    }

                    this.mode = 9;
                }

                var2 = this.distTree.getSymbol(this.input);
                if (var2 < 0) {
                    return false;
                }

                try {
                    this.repDist = CPDIST[var2];
                    this.neededBits = CPDEXT[var2];
                } catch (ArrayIndexOutOfBoundsException var4) {
                    throw new DataFormatException("Illegal rep dist code");
                }
            }

            if (this.neededBits > 0) {
                this.mode = 10;
                var3 = this.input.peekBits(this.neededBits);
                if (var3 < 0) {
                    return false;
                }

                this.input.dropBits(this.neededBits);
                this.repDist += var3;
            }

            this.outputWindow.repeat(this.repLength, this.repDist);
            var1 -= this.repLength;
        }

        return true;
    }

    private boolean decodeChksum() throws DataFormatException {
        while(this.neededBits > 0) {
            int var1 = this.input.peekBits(8);
            if (var1 < 0) {
                return false;
            }

            this.input.dropBits(8);
            this.readAdler = this.readAdler << 8 | var1;
            this.neededBits -= 8;
        }

        if ((int)this.adler.getValue() != this.readAdler) {
            throw new DataFormatException("Adler chksum doesn't match: " + Integer.toHexString((int)this.adler.getValue()) + " vs. " + Integer.toHexString(this.readAdler));
        } else {
            this.mode = 12;
            return false;
        }
    }

    private boolean decode() throws DataFormatException {
        int var2;
        switch(this.mode) {
            case 0:
                return this.decodeHeader();
            case 1:
                return this.decodeDict();
            case 2:
                if (this.isLastBlock) {
                    if (this.nowrap) {
                        this.mode = 12;
                        return false;
                    }

                    this.input.skipToByteBoundary();
                    this.neededBits = 32;
                    this.mode = 11;
                    return true;
                } else {
                    int var1 = this.input.peekBits(3);
                    if (var1 < 0) {
                        return false;
                    } else {
                        this.input.dropBits(3);
                        if ((var1 & 1) != 0) {
                            this.isLastBlock = true;
                        }

                        switch(var1 >> 1) {
                            case 0:
                                this.input.skipToByteBoundary();
                                this.mode = 3;
                                break;
                            case 1:
                                this.litlenTree = InflaterHuffmanTree.defLitLenTree;
                                this.distTree = InflaterHuffmanTree.defDistTree;
                                this.mode = 7;
                                break;
                            case 2:
                                this.dynHeader = new InflaterDynHeader();
                                this.mode = 6;
                                break;
                            default:
                                throw new DataFormatException("Unknown block type " + var1);
                        }

                        return true;
                    }
                }
            case 3:
                if ((this.uncomprLen = this.input.peekBits(16)) < 0) {
                    return false;
                } else {
                    this.input.dropBits(16);
                    this.mode = 4;
                }
            case 4:
                var2 = this.input.peekBits(16);
                if (var2 < 0) {
                    return false;
                } else {
                    this.input.dropBits(16);
                    if (var2 != (this.uncomprLen ^ '\uffff')) {
                        throw new DataFormatException("broken uncompressed block");
                    } else {
                        this.mode = 5;
                    }
                }
            case 5:
                var2 = this.outputWindow.copyStored(this.input, this.uncomprLen);
                this.uncomprLen -= var2;
                if (this.uncomprLen == 0) {
                    this.mode = 2;
                    return true;
                }

                return !this.input.needsInput();
            case 6:
                if (!this.dynHeader.decode(this.input)) {
                    return false;
                } else {
                    this.litlenTree = this.dynHeader.buildLitLenTree();
                    this.distTree = this.dynHeader.buildDistTree();
                    this.mode = 7;
                }
            case 7:
            case 8:
            case 9:
            case 10:
                return this.decodeHuffman();
            case 11:
                return this.decodeChksum();
            case 12:
                return false;
            default:
                throw new IllegalStateException();
        }
    }

    public void setDictionary(byte[] var1) {
        this.setDictionary(var1, 0, var1.length);
    }

    public void setDictionary(byte[] var1, int var2, int var3) {
        if (!this.needsDictionary()) {
            throw new IllegalStateException();
        } else {
            this.adler.update(var1, var2, var3);
            if ((int)this.adler.getValue() != this.readAdler) {
                throw new IllegalArgumentException("Wrong adler checksum");
            } else {
                this.adler.reset();
                this.outputWindow.copyDict(var1, var2, var3);
                this.mode = 2;
            }
        }
    }

    public void setInput(byte[] var1) {
        this.setInput(var1, 0, var1.length);
    }

    public void setInput(byte[] var1, int var2, int var3) {
        this.input.setInput(var1, var2, var3);
        this.totalIn += var3;
    }

    public int inflate(byte[] var1) throws DataFormatException {
        return this.inflate(var1, 0, var1.length);
    }

    public int inflate(byte[] var1, int var2, int var3) throws DataFormatException {
        if (var3 <= 0) {
            throw new IllegalArgumentException("len <= 0");
        } else {
            int var4 = 0;

            do {
                do {
                    if (this.mode != 11) {
                        int var5 = this.outputWindow.copyOutput(var1, var2, var3);
                        this.adler.update(var1, var2, var5);
                        var2 += var5;
                        var4 += var5;
                        this.totalOut += var5;
                        var3 -= var5;
                        if (var3 == 0) {
                            return var4;
                        }
                    }
                } while(this.decode());
            } while(this.outputWindow.getAvailable() > 0 && this.mode != 11);

            return var4;
        }
    }

    public boolean needsInput() {
        return this.input.needsInput();
    }

    public boolean needsDictionary() {
        return this.mode == 1 && this.neededBits == 0;
    }

    public boolean finished() {
        return this.mode == 12 && this.outputWindow.getAvailable() == 0;
    }

    public int getAdler() {
        return this.needsDictionary() ? this.readAdler : (int)this.adler.getValue();
    }

    public int getTotalOut() {
        return this.totalOut;
    }

    public int getTotalIn() {
        return this.totalIn - this.getRemaining();
    }

    public int getRemaining() {
        return this.input.getAvailableBytes();
    }

    /** @deprecated */
    public void end() {
        this.outputWindow = null;
        this.input = null;
        this.dynHeader = null;
        this.litlenTree = null;
        this.distTree = null;
        this.adler = null;
    }

    protected void finalize() {
    }
}
