//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class InflaterDynHeader {
    private static final int LNUM = 0;
    private static final int DNUM = 1;
    private static final int BLNUM = 2;
    private static final int BLLENS = 3;
    private static final int LLENS = 4;
    private static final int DLENS = 5;
    private static final int LREPS = 6;
    private static final int DREPS = 7;
    private static final int FINISH = 8;
    private byte[] blLens;
    private byte[] litlenLens;
    private byte[] distLens;
    private InflaterHuffmanTree blTree;
    private int mode;
    private int lnum;
    private int dnum;
    private int blnum;
    private int repBits;
    private byte repeatedLen;
    private int ptr;
    private static final int[] BL_ORDER = new int[]{16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};

    public InflaterDynHeader() {
    }

    public boolean decode(StreamManipulator var1) throws DataFormatException {
        while(true) {
            label189:
            while(true) {
                int var2;
                int var3;
                label187:
                while(true) {
                    label185:
                    while(true) {
                        switch(this.mode) {
                            case 0:
                                this.lnum = var1.peekBits(5);
                                if (this.lnum < 0) {
                                    return false;
                                }

                                this.lnum += 257;
                                var1.dropBits(5);
                                this.litlenLens = new byte[this.lnum];
                                this.mode = 1;
                            case 1:
                                this.dnum = var1.peekBits(5);
                                if (this.dnum < 0) {
                                    return false;
                                }

                                ++this.dnum;
                                var1.dropBits(5);
                                this.distLens = new byte[this.dnum];
                                this.mode = 2;
                            case 2:
                                this.blnum = var1.peekBits(4);
                                if (this.blnum < 0) {
                                    return false;
                                }

                                this.blnum += 4;
                                var1.dropBits(4);
                                this.blLens = new byte[19];
                                this.ptr = 0;
                                this.mode = 3;
                            case 3:
                                break;
                            case 4:
                                break label185;
                            case 5:
                                break label187;
                            case 6:
                                var2 = var1.peekBits(this.repBits);
                                if (var2 < 0) {
                                    return false;
                                }

                                var1.dropBits(this.repBits);

                                while(var2-- > 0) {
                                    if (this.ptr >= this.lnum) {
                                        throw new DataFormatException();
                                    }

                                    this.litlenLens[this.ptr++] = this.repeatedLen;
                                }

                                this.mode = 4;
                                continue;
                            case 7:
                                var2 = var1.peekBits(this.repBits);
                                if (var2 < 0) {
                                    return false;
                                }

                                var1.dropBits(this.repBits);

                                while(var2-- > 0) {
                                    if (this.ptr >= this.dnum) {
                                        throw new DataFormatException();
                                    }

                                    this.distLens[this.ptr++] = this.repeatedLen;
                                }

                                this.mode = 5;
                            default:
                                continue;
                        }

                        while(this.ptr < this.blnum) {
                            var2 = var1.peekBits(3);
                            if (var2 < 0) {
                                return false;
                            }

                            var1.dropBits(3);
                            this.blLens[BL_ORDER[this.ptr]] = (byte)var2;
                            ++this.ptr;
                        }

                        this.blTree = new InflaterHuffmanTree(this.blLens);
                        this.blLens = null;
                        this.ptr = 0;
                        this.mode = 4;
                        break;
                    }

                    while(this.ptr < this.lnum) {
                        var2 = this.blTree.getSymbol(var1);
                        if (var2 < 0) {
                            return false;
                        }

                        switch(var2) {
                            case 16:
                                if (this.ptr == 0) {
                                    throw new DataFormatException("Repeating, but no prev len");
                                }

                                this.repeatedLen = this.litlenLens[this.ptr - 1];
                                this.repBits = 2;

                                for(var3 = 3; var3-- > 0; this.litlenLens[this.ptr++] = this.repeatedLen) {
                                    if (this.ptr >= this.lnum) {
                                        throw new DataFormatException();
                                    }
                                }

                                this.mode = 6;
                                continue label187;
                            case 17:
                                this.repeatedLen = 0;
                                this.repBits = 3;

                                for(var3 = 3; var3-- > 0; this.litlenLens[this.ptr++] = this.repeatedLen) {
                                    if (this.ptr >= this.lnum) {
                                        throw new DataFormatException();
                                    }
                                }

                                this.mode = 6;
                                continue label187;
                            case 18:
                                this.repeatedLen = 0;
                                this.repBits = 7;

                                for(var3 = 11; var3-- > 0; this.litlenLens[this.ptr++] = this.repeatedLen) {
                                    if (this.ptr >= this.lnum) {
                                        throw new DataFormatException();
                                    }
                                }

                                this.mode = 6;
                                continue label187;
                            default:
                                this.litlenLens[this.ptr++] = (byte)var2;
                        }
                    }

                    this.ptr = 0;
                    this.mode = 5;
                    break;
                }

                while(this.ptr < this.dnum) {
                    var2 = this.blTree.getSymbol(var1);
                    if (var2 < 0) {
                        return false;
                    }

                    switch(var2) {
                        case 16:
                            if (this.ptr == 0) {
                                throw new DataFormatException("Repeating, but no prev len");
                            }

                            this.repeatedLen = this.distLens[this.ptr - 1];
                            this.repBits = 2;

                            for(var3 = 3; var3-- > 0; this.distLens[this.ptr++] = this.repeatedLen) {
                                if (this.ptr >= this.dnum) {
                                    throw new DataFormatException();
                                }
                            }

                            this.mode = 7;
                            continue label189;
                        case 17:
                            this.repeatedLen = 0;
                            this.repBits = 3;

                            for(var3 = 3; var3-- > 0; this.distLens[this.ptr++] = this.repeatedLen) {
                                if (this.ptr >= this.dnum) {
                                    throw new DataFormatException();
                                }
                            }

                            this.mode = 7;
                            continue label189;
                        case 18:
                            this.repeatedLen = 0;
                            this.repBits = 7;

                            for(var3 = 11; var3-- > 0; this.distLens[this.ptr++] = this.repeatedLen) {
                                if (this.ptr >= this.dnum) {
                                    throw new DataFormatException();
                                }
                            }

                            this.mode = 7;
                            continue label189;
                        default:
                            this.distLens[this.ptr++] = (byte)var2;
                    }
                }

                this.mode = 8;
                return true;
            }
        }
    }

    public InflaterHuffmanTree buildLitLenTree() throws DataFormatException {
        return new InflaterHuffmanTree(this.litlenLens);
    }

    public InflaterHuffmanTree buildDistTree() throws DataFormatException {
        return new InflaterHuffmanTree(this.distLens);
    }
}
