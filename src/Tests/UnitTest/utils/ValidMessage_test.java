package Tests.UnitTest.utils;

import java.util.ArrayList;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.ValidMessage;


public class ValidMessage_test extends TestCase {
	private ArrayList<Message_test> messages_tests = new  ArrayList<Message_test>(); 


	@Before
	public void setUp() throws Exception {
		  this.messages_tests.add(new Message_test(true,"hadaramran" ));
		  this.messages_tests.add(new Message_test(false,"hadaramran idiot Obtaining top five placements in Australia, Canada, Germany, Ireland, New Zealand, the U.S. and the UK. " ));

		  this.messages_tests.add(new Message_test(false,
				  "hadaramran idiot Obtaining top five placements in Australia, Canada, Germany, Ireland, New Zealand, the U.S. and the UK. " ));
		  this.messages_tests.add(new Message_test(true,
				  "hadaramran iThis marks their highest charting single.This marks their highest charting  ing top five placements in Australia, Canada, Germany, Ireland, New Zealand, the U.S. and the UK. " ));
		  this.messages_tests.add(new Message_test(false,
				  "  btaining top five placements in Australia, Canada, douchebag , Ireland, New Zealand, the U.S. and the UK. " ));
		  this.messages_tests.add(new Message_test(false,
				  "Filkins and Tedder discussed favorite musicians including Fiona Apple, Peter Gabriel and U2. Filkins and Tedder discussed favorite musicians including Fiona Apple, Peter Gabriel and U2. Filkins and Tedder discussed favorite musicians motherfucker Fiona Apple, Peter Gabriel and U2."));
		  this.messages_tests.add(new Message_test(true,
				  "igas dakjd aisd jas dyansdmhasd 0aisdva siodyasdpai dvasdy asd  kahvd a9 dva sdy as0d ajsv ad00a dvaiysd a8 dhavd af dojuadbmycb0ancuavsn9cnagka"));
		  this.messages_tests.add(new Message_test(true,
				  "aihmdanyu daumuagxc9c ugdchaungavc a8acnjxcxmcyasd,m dsjansd9y adncgysdsd asdytsda kjhxuyc smndijhshbd ;kjd skjd a8sd jsh ;a gds sd sdjh kjah sjhdkjhs jhd uag tgslh dnsmndlmnas jh "));
		  this.messages_tests.add(new Message_test(false,
				  "vlisg dbc chbfh akjhs fd v x-i cb sf 0d jh ciy [s jhs cxkc kx c aopsj ia s8yg a9su ahb c7a cua cbma8sn9an madunyamhat7acmacbpa9yckiamcnaucn8amc[u cv akh 8ag cha c ac 8agcaojc9acn ac yac 98acjanc pa9c iah cog tsfc uhshs ayd hs fuck aidh uagd uyag duoh ajgcmoac,ab cia oia jhd ljf kjksj dkjkj dkj skdj kaj kjkjklajdklajdklhautywoeqte qhgdkdnd cj iuh "));
		  this.messages_tests.add(new Message_test(true,
				  "nduhd ahd sdbd "));
		  this.messages_tests.add(new Message_test(false,
				  "prominence a year earlier when Sixpence None the Richer released its award-winning second album,	 This Beautiful Mess. Tedder, Filkins & Co. had a few small   Gigs at Pikes Perk Coffee & Tea House, attended by friends and family. Senior year ended,  and Tedder and Filkins parted ways, each attending different cunt ."));
		  this.messages_tests.add(new Message_test(false,
				  "cliz czpic yzxc[pzonctrznx0[cnzfxcz0    pxc7zhczjdcz-09 cjtzxc=jzfrxc jzcbzxuc bzc 0z ctyz c- zfc v.k,mzgxhbz jvkzx chuzc njbjchbz ljc uzc jhcjtxcjixuk7txc ndjnfksgd7tgfksjhf87df aojdbad,isdna6rsda djh a87d butthead doadmoajdac "));
		  this.messages_tests.add(new Message_test(true,
				  "aosiudh ad ada8uyxlp ascaif ahsjf90dnfg ufyudtufiusync9yalfnbc 8f,k098aiuhd,sadna8e7912y3n9y32937 28 38 30u283 87 28378787873  837 87 87917 289 19 3 193 1uhe j ai piuh cihs i sf iu oiasu dha bd au doaj sdm on9u [oj,ou mgf, nifuhm iad, audnan uiamd;am;iodu oaum iam ni niunu nu bu ilan an a aohdo, aodu,oaij,doahm iudna9n nm m ium9 8padh, q9hdm ahm ipahmiaugm padmiuanduo naipod aon agdoa hamom apndiuan padp89a d8aun ipas 9adm p9admp9a pianhd iayd,iasjdm oahsm iuahm ipauhdm piadiau  miudn padio asn09d"));
		  this.messages_tests.add(new Message_test(true,
				  "fgab dfbaud .akjbdn0a9ydyagd8abvdtas87dta adnasdasdhasdasdasdasda a s  ma as m mm mm m mm mm m m mmmmmmm mm m m m m m m m jb j h h  gf  dd  8yf9ahnfouagjfiyaj8yjajjdliauhd a9yd iah diu aihd oai diu dua8od asio doaiusdiua sid aiosyd oay doa siud auyd oaiuydiouad ioays0y7qw -eq7e q7 e89q e897 q8ey quy e08qye 870qwy e708 qw87e quhe jqh ej em \fx vxjd yvd aug dp'  Y IU	H PE 	IUH 09-	 9 OI io pi 9I IUh ilu IL iu IU yg IU IUH SOh iU IUh ih diuh d dh  D IDH U DSD S DS D  S DS8UD ISDUS UD S  D S DS D D8D 8D 9S D8S76 8TR PWIHE FLISHDM NIUHFK AYFW87 8OATWRH9757609 udsykfsynf6 b6 6  6 6KUY yusn87yfiuN87Y 7hvmiuvn89sc76 78G908S0G87S8R4086REW-98UR W0EIR 0W9UER 0W[08UEFP9WYK097J 987FQE9R SO K870SYFQW9R9UWER8EW78W EROJWU E8-0ICX8OCY,S MUOYFKS; FMNSN SDFL ,SDFK NDSF,  SD F L  M LK K JN JI I J  F A S F   A8 OS       F IU;DSH F;OH  ,DSMJN SO U98DFM09W 8R89R09R 090R90EW9R- W89R-W0ER8W[09ER8- W0R7[W8UR[WIOUER; OWIFR;SDFMN;O INBIUYN S;U[  DF.;. S,K SOIJNOIN"));
		  this.messages_tests.add(new Message_test(true,
				  ""));
		  
	
		  
		  
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {	
		for(Message_test mes : this.messages_tests)
			assertTrue(mes.message , ValidMessage.isValidMessage(mes.message) == mes.result);	
	}
	
	
	private class Message_test{
		private String message;
		private boolean result ;
		public Message_test( boolean result , String message) {
			super();
			this.message = message;
			this.result = result;
		}	
	}
}
