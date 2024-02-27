package finalproject;
import java.util.Scanner;
import java.util.random.RandomGenerator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;  
import java.io.IOException;
import java.io.BufferedWriter;


public class finalproject {

    public static void main(String[] args) {
    RandomIDGenerator ridg = new RandomIDGenerator();
    Center merkez = new Center();
    
    Car car1 = new Car();//İletişimci sınıfından yeni bir nesne oluşturdum
    merkez.addObject(car1);// Merkez nesnesinin koleksiyonuna eklemek için addObject metodunu çağırdım.
    
    Car car2 = new Car();
    merkez.addObject(car2);
    
    Cup cup1 = new Cup();
    merkez.addObject(cup1);
    
    Cup cup2 = new Cup();
    merkez.addObject(cup2);
    
    Cat cat1 = new Cat();
    merkez.addObject(cat1);
   
    Cat cat2 = new Cat();
    merkez.addObject(cat2);
    
    Cloud cloud1 = new Cloud();
    merkez.addObject(cloud1);
    
    Cloud cloud2 = new Cloud();
    merkez.addObject(cloud2);
    
    merkez.sendMessage(cloud1.getID()+":"+car2.getID()+":Merhaba");//cloud1 nesnesinden car2 nesnesine msaj göndermek için sendMessage metodunu çağırdım.
    merkez.sendMessage(cloud2.getID()+":"+car1.getID()+":quete");
    }
  }
class RandomIDGenerator{//Her bir iletişimci için random bir sayı üretiyoruz
	private Random random = new Random(); 
	private int randomID = random.nextInt(1000000);
	   
  Integer returnID() {
	  int ID = randomID;
	  return ID;

  }
}
	

class Center {
	private ArrayList<Communicator> storage;//Oluşturulacak her nesneyi koleksiyonun içerisine yerleştirmek için bir ArrayList oluşturuyoruz
	 
	public Center() {
		storage = new ArrayList<>();
	}
	public void addObject(Communicator object) {//Oluşturulan nesnleri koleksiyonun içerisine yerleştiriyoruz
		 storage.add(object);
		 sendMessage(object.getNewCommunicatorNotificationMessage());//Nesnenin oluşturulduğuna dair öncesinde oluşturulan ve koleksiyonun içerisinde olan yüm nesnelere mesaj gönderiyoruz
		 }
	
	public void removeObject(Communicator object) {//Oluşturulmuş iletişimcileri çıkartabilmek için bir metod oluşturdum
		 storage.remove(object);
		 }
	
	public ArrayList<Communicator> getstorage(){//İletişimcilere ulaşabilmek için ArrayListin içerisini gezen bir metod oluşturdum
		return storage;
	}
	
	 public void sendMessage(String msg) {
	    Message message = analyzeMessage(msg);//Mesajı analiz ederek 3'e ayırıyoruz 
	    if(message.message.equals("quete")) {//Eğer mesaj kısmında quete yazıyorsa alıntıyı gönderiyoruz
	    	storage.get(getCommunicatorIndexByID(message.srcID)).sendQuetitionMessage(message);
		    if (message.destID.equals("all")) {//Eğer alıcı kısmında all yazıyorsa tüm iletişimcilere mesajı göderiyoruz 
		    	sendMessageToAllCommunicator(message);
		    } else {
		    	storage.get(getCommunicatorIndexByID(message.destID)).receiveQueteMessage(message);//Eğer all yazılmamışsa sadece alıcıya gönderiliyor mesaj 
		    }
	    }else {//Bu kısımdaki tek fark alıntı yerine mesaj göndermesi
	    storage.get(getCommunicatorIndexByID(message.srcID)).sendMessage(message);
	    if (message.destID.equals("all")) {
	    	sendMessageToAllCommunicator(message);
	    } else {
	    	storage.get(getCommunicatorIndexByID(message.destID)).receiveMessage(message);
	    }
	    }
	 }
	 
	 void sendMessageToAllCommunicator(Message msg) {//Burada tüm iletişimcilere mesaj atan bir metod oluşturdum
	        for (int i = 0; i < storage.size(); i++) {//Koleksiyonun içerisini dolaşıyor ve eğer alıntı gönderilecekse alıntı eğer mesaj gönderilecekse yazılan mesajı gönderiyoruz
	            if (!storage.get(i).getID().equals(msg.srcID)) {
	                if (msg.message.equals("quete")) {
	                    storage.get(i).sendQuetitionMessage(msg);
	                } else {
	                    storage.get(i).receiveMessage(msg);
	                }
	            }
	        }
	    }
	 
	 Message analyzeMessage(String text) {//Bu kısımda .split kullanrak mesajı 3'e ayırıyoruz
		 String messageArray[] = text.split(":");
		 Message message = new Message(messageArray[0],messageArray[1],messageArray[2]);
		 return message;
	 }
	 
	 int getCommunicatorIndexByID(String id) {//İLetişimcilerin idlerine ulaşabilmek için tüm iletişimcileri dolaşan bir metod oluşturdum
		 for(int i = 0; i < storage.size(); i++) {
			 if (storage.get(i).getID().equals(id)) {
				 return i;
			 }
		 }
		 
		 // ID yi arraylistte bulamiyorsa hata donecek
		 return -1;
	 }
	
}

class Cup implements Communicator {
	private String ID;//iletişimcilerin ID özelliğine sahip olması için ID tanımlıyoruz
	private String[][] quete;//alıntıları saklayabilmek için 2 ye 2 lik bir dizi oljuşturuyoruz
	Random random;
	public Cup() {
		quete = new String[2][2];
		RandomIDGenerator ridg = new RandomIDGenerator();//Random Id oluşturabilmek için ridg adında nesneyi oluşturuyoruz
		this.ID = ridg.returnID().toString();
		quete[0][0] = "Asla sana sıradan gibi davranan birini sevme.";// 2 ye 2'lik dizinin indislerini doldurdum
		quete[0][1] = "Kendi araçları dahilinde yaşayan herkes hayal gücünün eksikliğinden muzdariptir.";
		quete[1][0] = "Mutsuzken, başkalarının mutsuzluğunu daha güçlü hissederiz; duygu parçalanmaz, yoğunlaşır...";
		quete[1][1] = "Mantıksız geldikleri için hislerimi inkar edecek kadar büyümemiştim daha.";
		random = new Random(2);
		
	}
	  public String getID() {
		        return ID;
		    }

			public void sendMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cup"+ID+".txt", true));//Mesajı gönderen için bir dosya oluşturuyoruz ve içerisine gönderilen mesajı alıcıyı ve göndereni belirten bir metin yazıyoruz
			        writer.write(m.destID+" ID Numarali Iletisimciye Gonderilen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
			
			public void receiveMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cup"+ID+".txt", true));//Bu kısımda aynı şeyi mesajı alan için yapıyoruz
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
	
			public String getNewCommunicatorNotificationMessage() {//TÜm nesnelere yeni bir nesne oluşturulduğu bilgisini vermesi için bir metod oluşturuyoruz
				return ID+":all:"+ID+" ID Numarasi ile yeni bir Cup iletisimcisi olusturuldu.";
			}
			public String returnQuete() {
				String queteMessage = quete[random.nextInt(2)][random.nextInt(2)];//alıntılardan random birisine dönen bir metod yazdım
				return queteMessage;
			}
			@Override
			public void sendQuetitionMessage(Message m) {		    
			    
			    try {
			        BufferedWriter writer = new BufferedWriter(new FileWriter("Cup" + ID + ".txt", true));//Üsttekinden tek farkı mesaj yerine alıntı kullanıyor
			        writer.write(m.srcID + " ID Numarali Iletisimciden Gelen Mesaj: " + returnQuete() + "\n");
			        writer.close();
			    } catch (IOException e) {
			        System.out.println("An error occurred.");
			    }
			}
			public void receiveQueteMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cup"+ID+".txt", true));
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+returnQuete()+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}

}

class Car implements Communicator {//İletişimci sınıfları birbirinin aynısı
	private String ID;
	private String[][] quete;
	Random random;
	public Car() {
		quete = new String[2][2];
		RandomIDGenerator ridg = new RandomIDGenerator();
		this.ID = ridg.returnID().toString();
		quete[0][0] = "Ölüm insanı bir kere, korku insanı bin kere öldürür.";
		quete[0][1] = "İnsan bildiğini terk etmeden bilmediğine ulaşamaz ve insan en çok da kendini bilmez.";
		quete[1][0] = "İhtiras her şeyi affettirir; siz ise, bu bencilliğiniz ile hep kendinizi düşünüyorsunuz.";
		quete[1][1] = "Her büyük sanatçı, sanata kendi damgasını vurur";
		random = new Random(2);
		
	}
	  public String getID() {
		        return ID;
		    }
		
			public void sendMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Car"+ID+".txt", true));
			        writer.write(m.destID+" ID Numarali Iletisimciye Gonderilen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
			
			public void receiveMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Car"+ID+".txt", true));
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
			
			public String getNewCommunicatorNotificationMessage() {
				return ID+":all:"+ID+" ID Numarasi ile yeni bir Car iletisimcisi olusturuldu.";
			}
			public String returnQuete() {
				String queteMessage = quete[random.nextInt(2)][random.nextInt(2)];
				return queteMessage;
			}
			@Override
			public void sendQuetitionMessage(Message m) {
			    Random random = new Random();
			   
			    try {
			        BufferedWriter writer = new BufferedWriter(new FileWriter("Car" + ID + ".txt", true));
			        writer.write(m.srcID + " ID Numarali Iletisimciden Gönderilen Mesaj: " + returnQuete() + "\n");
			        writer.close();
			    } catch (IOException e) {
			        System.out.println("An error occurred.");
			    }
			}
			public void receiveQueteMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Car"+ID+".txt", true));
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+returnQuete()+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}

 
}

class Cat implements Communicator {//İletişimci sınıfları birbirinin aynısı
	private String ID;
	private String[][] quete;
	Random random;
	public Cat() {
		quete = new String[2][2];
		RandomIDGenerator ridg = new RandomIDGenerator();
		this.ID = ridg.returnID().toString();
		quete[0][0] = "Beşikten mezara kadar bilim öğrenin";
		quete[0][1] = "Geç kalan teselli, idam dan sonraki affa benzer";
		quete[1][0] = "Eğri cetvelden doğru çizgi çıkmaz";
		quete[1][1] = "Güzellik ile akıl nâdiren birarada bulunurlar";
		random = new Random(2);
	}
	
	  public String getID() {
		        return ID;
		    }
		
			public void sendMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cat"+ID+".txt", true));
			        writer.write(m.destID+" ID Numarali Iletisimciye Gonderilen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
			
			public void receiveMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cat"+ID+".txt", true));
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+m.message+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}
			
			public String getNewCommunicatorNotificationMessage() {
				return ID+":all:"+ID+" ID Numarasi ile yeni bir Cat iletisimcisi olusturuldu.";
			}
			public String returnQuete() {
				String queteMessage = quete[random.nextInt(2)][random.nextInt(2)];
				return queteMessage;
			}

			@Override
			public void sendQuetitionMessage(Message m) {
			    Random random = new Random();
			    
			    try {
			        BufferedWriter writer = new BufferedWriter(new FileWriter("Cat" + ID + ".txt", true));
			        writer.write(m.srcID + " ID Numarali Iletisimciden Gönderilen Mesaj: " + returnQuete() + "\n");
			        writer.close();
			    } catch (IOException e) {
			        System.out.println("An error occurred.");
			    }
			}
			public void receiveQueteMessage(Message m) {
			    try {
			    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cat"+ID+".txt", true));
			        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+returnQuete()+"\n");
			        writer.close();
			      } catch (IOException e) {
			        System.out.println("An error occurred.");
			      }
			}

	
}

class Cloud implements Communicator{//İletişimci sınıfları birbirinin aynısı
	private String ID;
	private String[][] quete;
	Random random;
	public Cloud() {
		quete = new String[2][2];
		RandomIDGenerator ridg = new RandomIDGenerator();
		this.ID = ridg.returnID().toString();
		quete[0][0] = "İki günü eşit olan ziyandadır";
		quete[0][1] = "Küçüklerin büyüklük taslaması kadar tehlikeli bir şey yoktur";
		quete[1][0] = "Nezaket hiçten gelir; ama her şeyi satın alır\r\n";
		quete[1][1] = "Okullar demokrasinin kalesidir";
		random = new Random(2);
		
		
	}
	  public String getID() {
		        return ID;
		    }

		public void sendMessage(Message m) {
		    try {
		    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cloud"+ID+".txt", true));
		    	writer.write(m.destID+" ID Numarali Iletisimciye Gonderilen Mesaj: "+m.message+"\n");
		    	writer.close();
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		      }
		}
		
		public void receiveMessage(Message m) {
		    try {
		    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cloud"+ID+".txt", true));
		        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+m.message+"\n");
		        writer.close();
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		      }
		}
		
		public String getNewCommunicatorNotificationMessage() {
			return ID+":all:"+ID+" ID Numarasi ile yeni bir Cloud iletisimcisi olusturuldu.";
		}
		public String returnQuete() {
			String queteMessage = quete[random.nextInt(2)][random.nextInt(2)];
			return queteMessage;
		}
		@Override
		public void sendQuetitionMessage(Message m) {
		    
		    try {
		        BufferedWriter writer = new BufferedWriter(new FileWriter("Cloud" + ID + ".txt", true));
		        writer.write(m.srcID + " ID Numarali Iletisimciden Gönderilen Mesaj: " + returnQuete() + "\n");
		        writer.close();
		    } catch (IOException e) {
		        System.out.println("An error occurred.");
		    }
		}
		public void receiveQueteMessage(Message m) {
		    try {
		    	BufferedWriter writer = new BufferedWriter(new FileWriter("Cloud"+ID+".txt", true));
		        writer.write(m.srcID+" ID Numarali Iletisimciden Gelen Mesaj: "+returnQuete()+"\n");
		        writer.close();
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		      }
		}
		

}

interface Communicator {//İletişimci sınıflarındaki metodları kullanabilmek için bir arayüz oluşturdum
    public String getID();
    public void sendMessage(Message m);
    public void receiveMessage(Message m);
    public String getNewCommunicatorNotificationMessage();
    public void sendQuetitionMessage(Message m);
    public void receiveQueteMessage(Message m);
}


class Message {//Mesajı 3'e böldüğümüzde değişkenlere atayıp kullanabilmek için bu sınıfı oluşturdum
	String srcID;
	String destID;
	String message;
	
	public Message(String srcID, String destID, String message) {
		this.destID = destID;
		this.srcID = srcID;
		this.message = message;
	}
}

