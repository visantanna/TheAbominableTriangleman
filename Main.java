import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		List<Ponto> trianguloMagico = new ArrayList<Ponto>();
		List<Ponto> listaDePontos = new ArrayList<Ponto>();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()){
			for(int i = 0 ;i < 3; i ++ ){
				int x = sc.nextInt();
				int y = sc.nextInt();
				trianguloMagico.add(new Ponto(x,y));
			}
			int qtdPontos = sc.nextInt();
			for(int j = 0 ; j < qtdPontos; j++){
				int x = sc.nextInt();
				int y = sc.nextInt();
				listaDePontos.add(new Ponto(x,y));
			}
			executar(trianguloMagico , listaDePontos);
			trianguloMagico.clear();
			listaDePontos.clear();
			if(sc.hasNext())
				sc.nextLine();
		}

	}
	private static void executar(List<Ponto> trianguloMagico, List<Ponto> listaDePontos) {
		double distXY = trianguloMagico.get(0).distancia(trianguloMagico.get(1));
		double distXZ = trianguloMagico.get(0).distancia(trianguloMagico.get(2));
		double distYZ = trianguloMagico.get(1).distancia(trianguloMagico.get(2));
		List<Ponto> Resultado = new ArrayList<Ponto>();
		
		for(int i = 0 ; i < listaDePontos.size() ; i ++){
			List<Ponto> listaDistXY = new ArrayList<Ponto>();
			List<Ponto> listaDistXZ = new ArrayList<Ponto>();
			for(int j = 0; j < listaDePontos.size() ; j ++){
				
				Ponto ponto1 = listaDePontos.get(i);
				Ponto ponto2 = listaDePontos.get(j);
				
				double distancia = ponto1.distancia(ponto2);
				
				int adicionado = 0;
				if(distXY == distXZ){
					if(distancia == distXY){
						listaDistXY.add(ponto2);
						listaDistXZ.add(ponto2);
						adicionado =1;
					}
				}else if(distancia == distXY){
					listaDistXY.add(ponto2);
					adicionado =1;
				}else if(distancia == distXZ){
						listaDistXZ.add(ponto2);
						adicionado =2;
				}
				Ponto pontoConfirmado = null;
				
				if(adicionado > 0){
					if(adicionado == 1){
						Ponto lastAdded = listaDistXY.get(listaDistXY.size()-1);
						for(Ponto ponto : listaDistXZ){
							if(lastAdded.distancia(ponto) == distYZ ){
								pontoConfirmado = ponto;
								break;
							}
						}
					}else{
						Ponto lastAdded = listaDistXZ.get(listaDistXZ.size()-1);
						for(Ponto ponto : listaDistXY){
							if(lastAdded.distancia(ponto) == distYZ ){
								pontoConfirmado = ponto;
								break;
							}
						}
					}
					if(pontoConfirmado != null){
						Resultado.add(ponto1);
						Resultado.add(ponto2);
						Resultado.add(pontoConfirmado);
						PontoComparator comparador = new PontoComparator();
						Resultado.sort(comparador);
				
						Resultado.forEach(ponto -> System.out.println(ponto.x() + " " +ponto.y()));
						System.out.println();
						return;
					}
				}
			}
		}
	}
}
class Ponto implements Comparable<Ponto>{
	private int x;
	private int y;
	Ponto(int x , int y){
		this.x = x;
		this.y = y;
	} 
	public int x(){
		return x;
	}
	public int y(){
		return y;
	}
	public double distancia(Ponto ponto2){
		int distX = Math.abs(x - ponto2.x());
		int distY = Math.abs(y - ponto2.y());
		double distPontos = Math.sqrt(distX*distX + distY*distY);
		return distPontos;
	}
	@Override
	public int compareTo(Ponto ponto) {
		if(x > ponto.x()){
			return 1;
		}else if(x < ponto.x()){
			return -1;
		}else if(x == ponto.x()){
			if(y >= ponto.y()){
				return 1;
			}else{
				return -1;
			}
		}
		return 0;
	}
}
class PontoComparator implements Comparator<Ponto>{

	@Override
	public int compare(Ponto o1, Ponto o2) {
		return o1.compareTo(o2);
	}

}
