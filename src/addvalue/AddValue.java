/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addvalue;

import Funciones.Funciones_Image;
import Funciones.IOBinFile;
import Funciones.MyListArgs;
import Funciones.MySintaxis;
import java.awt.Color;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Miguel
 */
public class AddValue {

    static float[] pixeles;
    
    int bit = 255;
    /**
     * @param args the command line arguments
     */
    AddValue( String []Args) throws IOException{
        String ConfigFile, Archivo, DIR;   
        MyListArgs Param;
        BufferedImage image;
        boolean formato = false;
        String value;
        String[] aux;
        int R = 0,G=0,B=0;
        Param = new MyListArgs(Args);
        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");
        if (!ConfigFile.equals("")) {
            Param.AddArgsFromFile(ConfigFile);
        }//fin if
        String Sintaxis = "-IMG:str -DIR:str -COLOR:str";
        MySintaxis Review = new MySintaxis(Sintaxis, Param);
        //PARAMETROS FORZOSOS
        DIR = Param.ValueArgsAsString("-DIR", "");
        Archivo = Param.ValueArgsAsString("-IMG", "");
        value = Param.ValueArgsAsString("-COLOR","");
        aux = value.split("\\W");

        for(int i = 0;i<aux.length;i++){
            if(aux[i].contains("R")|aux[i].contains("r"))
                R = Integer.parseInt(aux[i].substring(1, aux[i].length()));               
            if(aux[i].contains("G")|aux[i].contains("g"))           
                G = Integer.parseInt(aux[i].substring(1, aux[i].length()));
            if(aux[i].contains("B")|aux[i].contains("b"))           
                B = Integer.parseInt(aux[i].substring(1, aux[i].length()));
            
        }
        if(R+G+B==0){
            System.out.println("PARAMETROS NO VALIDOS");
        }else{
            if( R > 255 ){
                System.out.println("El parametro R no debe exeder 255,\n R se igualo a 255");
                R = 255;
            }
            if( G > 255 ){
                System.out.println("El parametro G no debe exeder 255,\n G se igualo a 255");
                G = 255;
            }
            if( B > 255 ){
                System.out.println("El parametro B no debe exeder 255,\nB se igualo a 255");
                B = 255;
            }
            //if (Archivo.endsWith(".img")|Archivo.endsWith(".IMG"))
            //    image = Funciones_Image.abreIMG(Archivo);
            //else
                image = ImageIO.read(new File(Archivo));
            int contador = 0;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    Color n = new Color(image.getRGB(i, j));
                    
                    if(n.getRed()<255)
                    {    contador++;
                        System.out.println(n.getRed());
                    
                    }
                        
                }
            }
            System.out.println("cuenta:"+contador);
            if(R>0)
                image = ADDred( image, R );       
            if(G>0)
                image = ADDgreen( image, G );       
            if(B>0)
                image = ADDblue( image,B );

            if (DIR.endsWith(".img") || DIR.endsWith(".IMG")) {
                formato = true;
            } else if (DIR.endsWith(".jpg") || DIR.endsWith(".JPG") || DIR.endsWith(".TIFF")
                    || DIR.endsWith(".tiff") || DIR.endsWith(".gif") || DIR.endsWith(".GIF")
                    || DIR.endsWith(".BMP") || DIR.endsWith(".bmp") || DIR.endsWith(".png")
                    || DIR.endsWith(".PNG") || DIR.endsWith(".WBMP") || DIR.endsWith(".wbmp")) {
                formato = false;
            }
            if (formato == true) {
                pixeles = Funciones_Image.BufferAVector(image);
                IOBinFile.WriteBinFloatFileIEEE754(DIR, pixeles);
            } else if (formato == false) {
                Funciones_Image.ImagenAFormato(image, DIR);
            }
        }    
    }

    private BufferedImage ADDred(BufferedImage image, int value) {
        BufferedImage biDestino = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);
        int r,g,b;
        //Recorrer las imagenes y obtiene el color de la imagen original y la almacena en el destino con tonalidad en grises<
        for( int x = 0; x < image.getWidth(); x++ ){
            for( int y = 0; y < image.getHeight(); y++ ) {
                //Obtiene el color de la imagen original
                Color c1 = new Color(image.getRGB(x, y));
                //almacenar los valores RGB de cada pixel individualmente
                if( (r = c1.getRed  ()+value)>bit )
                    r = bit;
                g = c1.getGreen();
                b = c1.getBlue ();
                //almacena cada uno de los valores aplicando la formula
                biDestino.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return biDestino;
    }

    private BufferedImage ADDgreen(BufferedImage image, int value) {
        BufferedImage biDestino = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);
        int r,g,b;
        //Recorrer las imagenes y obtiene el color de la imagen original y la almacena en el destino con tonalidad en grises<
        for( int x = 0; x < image.getWidth(); x++ ){
            for( int y = 0; y < image.getHeight(); y++ ) {
                //Obtiene el color de la imagen original
                Color c1 = new Color(image.getRGB(x, y));
                //almacenar los valores RGB de cada pixel individualmente
                r = c1.getRed  ();
                if( (g = c1.getGreen  ()+value)>bit )
                    g = bit;
                b = c1.getBlue ();
                //almacena cada uno de los valores aplicando la formula
                biDestino.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return biDestino;
    }
 
    private BufferedImage ADDblue(BufferedImage image, int value) {
        BufferedImage biDestino = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);
        int r,g,b;
        //Recorrer las imagenes y obtiene el color de la imagen original y la almacena en el destino con tonalidad en grises<
        for( int x = 0; x < image.getWidth(); x++ ){
            for( int y = 0; y < image.getHeight(); y++ ) {
                //Obtiene el color de la imagen original
                Color c1 = new Color(image.getRGB(x, y));
                
                //almacenar los valores RGB de cada pixel individualmente
                r = c1.getRed  ();
                g = c1.getGreen  ();
                if( (b = c1.getBlue  ()+value)>bit )
                    b = 255;
                //almacena cada uno de los valores aplicando la formula
                biDestino.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return biDestino;
    }
}
            
