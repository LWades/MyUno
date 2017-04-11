package com.example.dell.myuno;



public class CardGroup {
	Card[] CardGroup = new Card[108];
	Card[] card = new Card[108];
	int[] array = new int[108];
	int pos = 107;
	
	
	public CardGroup() {
		card[0] = new Card(Color.Red, Type.Number,0,0);
		card[1] = new Card(Color.Yellow,Type.Number,0,0);
		card[2] = new Card(Color.Blue,Type.Number,0,0);
		card[3] = new Card(Color.Green,Type.Number,0,0);
		card[4] = new Card(Color.Red,Type.Number,1,0);
		card[5] = new Card(Color.Yellow,Type.Number,1,0);
		card[6] = new Card(Color.Blue,Type.Number,1,0);
		card[7] = new Card(Color.Green,Type.Number,1,0);
		card[8] = new Card(Color.Red,Type.Number,1,1);
		card[9] = new Card(Color.Yellow,Type.Number,1,1);
		card[10] = new Card(Color.Blue,Type.Number,1,1);
		card[11] = new Card(Color.Green,Type.Number,1,1);
		card[12] = new Card(Color.Red,Type.Number,2,0);
		card[13] = new Card(Color.Yellow,Type.Number,2,0);
		card[14] = new Card(Color.Blue,Type.Number,2,0);
		card[15] = new Card(Color.Green,Type.Number,2,0);
		card[16] = new Card(Color.Red,Type.Number,2,1);
		card[17] = new Card(Color.Yellow,Type.Number,2,1);
		card[18] = new Card(Color.Blue,Type.Number,2,1);
		card[19] = new Card(Color.Green,Type.Number,2,1);
		card[20] = new Card(Color.Red,Type.Number,3,0);
		card[21] = new Card(Color.Yellow,Type.Number,3,0);
		card[22] = new Card(Color.Blue,Type.Number,3,0);
		card[23] = new Card(Color.Green,Type.Number,3,0);
		card[24] = new Card(Color.Red,Type.Number,3,1);
		card[25] = new Card(Color.Yellow,Type.Number,3,1);
		card[26] = new Card(Color.Blue,Type.Number,3,1);
		card[27] = new Card(Color.Green,Type.Number,3,1);
		card[28] = new Card(Color.Red,Type.Number,4,0);
		card[29] = new Card(Color.Yellow,Type.Number,4,0);
		card[30] = new Card(Color.Blue,Type.Number,4,0);
		card[31] = new Card(Color.Green,Type.Number,4,0);
		card[32] = new Card(Color.Red,Type.Number,4,1);
		card[33] = new Card(Color.Yellow,Type.Number,4,1);
		card[34] = new Card(Color.Blue,Type.Number,4,1);
		card[35] = new Card(Color.Green,Type.Number,4,1);
		card[36] = new Card(Color.Red,Type.Number,5,0);
		card[37] = new Card(Color.Yellow,Type.Number,5,0);
		card[38] = new Card(Color.Blue,Type.Number,5,0);
		card[39] = new Card(Color.Green,Type.Number,5,0);
		card[40] = new Card(Color.Red,Type.Number,5,1);
		card[41] = new Card(Color.Yellow,Type.Number,5,1);
		card[42] = new Card(Color.Blue,Type.Number,5,1);
		card[43] = new Card(Color.Green,Type.Number,5,1);
		card[44] = new Card(Color.Red,Type.Number,6,0);
		card[45] = new Card(Color.Yellow,Type.Number,6,0);
		card[46] = new Card(Color.Blue,Type.Number,6,0);
		card[47] = new Card(Color.Green,Type.Number,6,0);
		card[48] = new Card(Color.Red,Type.Number,6,1);
		card[49] = new Card(Color.Yellow,Type.Number,6,1);
		card[50] = new Card(Color.Blue,Type.Number,6,1);
		card[51] = new Card(Color.Green,Type.Number,6,1);
		card[52] = new Card(Color.Red,Type.Number,7,0);
		card[53] = new Card(Color.Yellow,Type.Number,7,0);
		card[54] = new Card(Color.Blue,Type.Number,7,0);
		card[55] = new Card(Color.Green,Type.Number,7,0);
		card[56] = new Card(Color.Red,Type.Number,7,1);
		card[57] = new Card(Color.Yellow,Type.Number,7,1);
		card[58] = new Card(Color.Blue,Type.Number,7,1);
		card[59] = new Card(Color.Green,Type.Number,7,1);
		card[60] = new Card(Color.Red,Type.Number,8,0);
		card[61] = new Card(Color.Yellow,Type.Number,8,0);
		card[62] = new Card(Color.Blue,Type.Number,8,0);
		card[63] = new Card(Color.Green,Type.Number,8,0);
		card[64] = new Card(Color.Red,Type.Number,8,1);
		card[65] = new Card(Color.Yellow,Type.Number,8,1);
		card[66] = new Card(Color.Blue,Type.Number,8,1);
		card[67] = new Card(Color.Green,Type.Number,8,1);
		card[68] = new Card(Color.Red,Type.Number,9,0);
		card[69] = new Card(Color.Yellow,Type.Number,9,0);
		card[70] = new Card(Color.Blue,Type.Number,9,0);
		card[71] = new Card(Color.Green,Type.Number,9,0);
		card[72] = new Card(Color.Red,Type.Number,9,1);
		card[73] = new Card(Color.Yellow,Type.Number,9,1);
		card[74] = new Card(Color.Blue,Type.Number,9,1);
		card[75] = new Card(Color.Green,Type.Number,9,1);
		card[76] = new Card(Color.Red,Type.Skip,10,0);
		card[77] = new Card(Color.Yellow,Type.Skip,10,0);
		card[78] = new Card(Color.Blue,Type.Skip,10,0);
		card[79] = new Card(Color.Green,Type.Skip,10,0);
		card[80] = new Card(Color.Red,Type.Skip,10,1);
		card[81] = new Card(Color.Yellow,Type.Skip,10,1);
		card[82] = new Card(Color.Blue,Type.Skip,10,1);
		card[83] = new Card(Color.Green,Type.Skip,10,1);
		card[84] = new Card(Color.Red,Type.Reverse,10,0);
		card[85] = new Card(Color.Yellow,Type.Reverse,10,0);
		card[86] = new Card(Color.Blue,Type.Reverse,10,0);
		card[87] = new Card(Color.Green,Type.Reverse,10,0);
		card[88] = new Card(Color.Red,Type.Reverse,10,1);
		card[89] = new Card(Color.Yellow,Type.Reverse,10,1);
		card[90] = new Card(Color.Blue,Type.Reverse,10,1);
		card[91] = new Card(Color.Green,Type.Reverse,10,1);
		card[92] = new Card(Color.Red,Type.Draw_Two,10,0);
		card[93] = new Card(Color.Yellow,Type.Draw_Two,10,0);
		card[94] = new Card(Color.Blue,Type.Draw_Two,10,0);
		card[95] = new Card(Color.Green,Type.Draw_Two,10,0);
		card[96] = new Card(Color.Red,Type.Draw_Two,10,1);
		card[97] = new Card(Color.Yellow,Type.Draw_Two,10,1);
		card[98] = new Card(Color.Blue,Type.Draw_Two,10,1);
		card[99] = new Card(Color.Green,Type.Draw_Two,10,1);
		card[100] = new Card(Color.Black,Type.Wild,10,0);
		card[101] = new Card(Color.Black,Type.Wild,10,0);
		card[102] = new Card(Color.Black,Type.Wild,10,0);
		card[103] = new Card(Color.Black,Type.Wild,10,0);
		card[104] = new Card(Color.Black,Type.Wild_Draw_Four,10,0);
		card[105] = new Card(Color.Black,Type.Wild_Draw_Four,10,0);
		card[106] = new Card(Color.Black,Type.Wild_Draw_Four,10,0);
		card[107] = new Card(Color.Black,Type.Wild_Draw_Four,10,0);
		
		for(int i = 0;i < 108; i++){
			array[i] = i;
		}	
		Shuffle();
	}
	
	public void swap(int[] a,int i,int j){
		if(i!=j){
			a[i]=a[i]+a[j];
			a[j]=a[i]-a[j];
			a[i]=a[i]-a[j];
		}
	}
	
	public void Shuffle(){
		for(int i=0;i<108;i++){
			int j=(int)(108*Math.random());
			swap(array,i,j);
		}
		
		for (int i = 0; i < 108; i++)
			CardGroup[array[i]] = card[i];
	}
	public Card draw(){
		if(pos==0){
			return CardGroup[0];
		}
		else{
			pos-=1;
			return CardGroup[pos+1];
		}
	}
	
	public static void main(String[] args){
		CardGroup g =new CardGroup();
		g.Shuffle();
		for (int i = 0; i < 107; i++)
		g.CardGroup[i].output();
	}
}
