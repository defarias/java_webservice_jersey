package com.webservice.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webservice.pojo.Imagem;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

@Path("imagem")
public class ImagemDAO {
	private static final String LOCATION_FOLDER = "C://uploads/";
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Imagem upload(String json){

		Imagem imagem = construirObjeto(json);
		byte[] data = DatatypeConverter.parseBase64Binary(imagem.getImage());
		String filePath = LOCATION_FOLDER+imagem.getNome();
		boolean retorno = save(data, filePath);
		
		String output = "";
		if(retorno){
			output = filePath;
			imagem.setImage(output);
			return imagem;
		}	
		return null;
	}
	
	@POST
	@Path("download")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Imagem download(String json) throws IOException{
		JSONObject objetoJson = new JSONObject(json);
		String caminho = objetoJson.getString("caminho"); 
		
		System.out.println(caminho);
		
		BufferedImage image = ImageIO.read(new File(LOCATION_FOLDER+caminho));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		
		byte[] byteArray = baos.toByteArray();
		String image64 = DatatypeConverter.printBase64Binary(byteArray);
		
		Imagem imagem = new Imagem();
		imagem.setNome(caminho);
		imagem.setImage(image64);

		System.out.println("Imagem 64:"+image64);
		
		return imagem;
	}
	
	private boolean save(byte[] data, String filePath) {
		try{
			FileOutputStream fos = new FileOutputStream(filePath); 
		    fos.write(data); 
		    fos.close();
		    
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	    return true;

	}
	
	private Imagem construirObjeto(String json){
		System.out.println("JSON: "+json);
		
		Gson gson = new Gson();
		Imagem imagem = new Imagem();
        Type usuarioType = new TypeToken<Imagem>(){}.getType();
        imagem = gson.fromJson(json, usuarioType);
		
        System.out.println("JSON: "+imagem.getNome());
		return imagem;

	}

}
