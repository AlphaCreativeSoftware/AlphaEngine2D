package org.alphacreative.graphics;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Loader //Clase que contiene el cargador de imagenes otorgando un path donde se ubique
{
    public static BufferedImage ImageLoader(String path)
    {
        try {
            return ImageIO.read(Loader.class.getResource(path));//Se debe usar try catch
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Font FontLoader(String path, int size)
    {
        try
        {
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN,size);
        }
        catch (Exception e)
        {
            System.out.println("Excepción al cargar fuente");
            return null;
        }
    }

    public static Clip SoundLoader(String path) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
			return clip;
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		return null;
	}
}