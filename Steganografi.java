/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author bim
 */
public class Steganografi {
    static int[][] citra = {
        {196,10,97,182,101,40},
        {67,200,100,50,90,50},
        {25,150,45,200,75,28},
        {176,56,77,100,25,200},
        {101,34,250,40,100,60},
        {44,66,99,125,190,200}
    };
            
    public static void main(String[] args) {
        String kata = "aku#";
        hitungKata(kata);
        
        System.out.println("Before");
        String[][] cb = citraBiner();
        for (int i = 0; i < cb.length; i++) {
            for (int j = 0; j < cb[0].length; j++) {
                System.out.print(cb[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nAfter");
        String[][] ca = encryptLSB(kata);
        for (int i = 0; i < ca.length; i++) {
            for (int j = 0; j < ca[0].length; j++) {
                System.out.print(ca[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println();
        int[][] num = bin2dec(ca);
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[0].length; j++) {
                System.out.print(num[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
    public static void hitungKata(String kata) {
        int m = citra.length;
        int n = citra[0].length;
        int c = kata.length();
        int hitung = (m * n) / 8;
        
        if(hitung < c) {
            System.out.println("Karakter tidak boleh lebih dari "+hitung);
            System.exit(0);
        }
    }
    
    public static char[] keyStegano(String kata) {
        char[] aux_bin = new char[kata.length()*7];
        char[] cKata = kata.toCharArray();
        int ascii, hit=0;
        String biner;
        
        for(int i=0; i<cKata.length; i++) {
            ascii = (int)cKata[i];
            biner = Integer.toBinaryString(ascii);
            if(biner.length() < 7) {
                biner = "0"+biner;
            }
            
            for(int j=0; j<biner.length(); j++) {
                aux_bin[hit] = biner.charAt(j);
                hit++;
            }
        }
        
        return aux_bin;
    }

    public static String[][] citraBiner() {
        String[][] output = new String[citra.length][citra[0].length];
        
        for(int i=0; i<citra.length; i++) {
            for(int j=0; j<citra[0].length; j++) {
                String out = Integer.toBinaryString(citra[i][j]);
                if(out.length() < 8) {
                    if(out.length() == 7) {
                        out = "0"+out;
                    } else if(out.length() == 6) {
                        out = "00"+out;
                    } else if(out.length() == 5) {
                        out = "000"+out;
                    } else if(out.length() == 4) {
                        out = "0000"+out;
                    } else if(out.length() == 3) {
                        out = "00000"+out;
                    } else if(out.length() == 2) {
                        out = "000000"+out;
                    } else if(out.length() == 1) {
                        out = "0000000"+out;
                    } else if(out.length() == 0) {
                        out = "00000000";
                    }
                }
                
                output[i][j] = out;
            }
        }
        
        return output;
    }

    public static String[][] encryptLSB(String kata) {
        char[] aux = keyStegano(kata);
        String[][] cb = citraBiner();
        String[][] out = new String[cb.length][cb[0].length];
        int aux_length = aux.length;
        int hit=0;
        
        for(int i=0; i<cb.length; i++) {
            for(int j=0; j<cb[0].length; j++) {
                String bin = cb[j][i];
                char[] last_char = bin.toCharArray();
                if(hit < aux_length) {
                    last_char[7] = aux[hit];
                }
                
                bin = String.valueOf(last_char);
                out[j][i] = bin;
                hit++;
            }
        }
        
        return out;
    }
    
    public static int[][] bin2dec(String[][] num) {
        int[][] out = new int[num.length][num[0].length];
        
        for(int i=0; i<num.length; i++) {
            for(int j=0; j<num[0].length; j++) {
                int sum = 0;
                int hit = 7;
                
                for(int k=0; k<num[i][j].length(); k++) {
                    char aux1 =  num[i][j].charAt(k);
                    String aux2 = String.valueOf(aux1);
                    int aux3 = Integer.parseInt(aux2);
                    
                    sum += (int)(aux3 * Math.pow(2,hit));
                    hit--;
                }
                
                out[i][j] = sum;
            }
        }
        
        return out;
    }
}