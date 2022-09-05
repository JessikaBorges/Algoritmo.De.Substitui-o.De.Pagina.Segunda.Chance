import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Program {


		public static void main(String[] args) {
			
			File caminho = new File("C:/Users/Jessika Borges/eclipse-workspace/Sistemas Operacionais/");
			File arquivo = new File("Entrada.txt");
			
			try {
				FileReader lerArquivo = new FileReader(arquivo);
				//linha por linha
				BufferedReader bufferedLer = new BufferedReader(lerArquivo);
				
				//Armazenar a linha
				String linha = "";
				linha = bufferedLer.readLine();
				int quantidadeDeQuadros = Integer.parseInt(linha);
				
				//Criar lista de String
				List<String> listaString = new ArrayList<String>();
				
				//ler linha e adicionar na lista
				while ((linha = bufferedLer.readLine()) != null) {
					listaString.add(linha);
				} 
				//imprimir
				System.out.println(listaString);
				segundaChance(quantidadeDeQuadros, listaString);
				
			} catch (IOException e) {
				e.printStackTrace();
				
				
			}
			
		}

		static void segundaChance(int size,List<String> listaString) {
			ArrayDeque<Processo> cache = new ArrayDeque<>(); //remover da primeira fila e adiconar no final

			int qtdDeFaltasDePagina = 0;

			for (String d : listaString) { 
				boolean contem = false;
				for (Processo p : cache) {
					if(p.nome.equalsIgnoreCase(d)) {
						contem = true;
						break;
					}
				}
				if (!contem) {
					if (cache.size() < size) { // se for 1 zera o bit e adiciona no final da fila
						cache.add(new Processo(d, 1));
						System.out.println("Pagina nao encontrado (page fault), adicionado no fim da fila"); 
					} else {
						while (true) {
							if (cache.getFirst().r == 1) {
								Processo p = cache.poll();
								p.r = 0;
								cache.add(p);
							} else { // se 0 será removido e adicionado outro processo
								System.out.println("Pagina nao encontrado (page fault), uma pagina foi removida , adicionado no fim da fila");
								cache.remove(); 
								cache.add(new Processo(d, 1));
								break;
							}
						}
					}

					qtdDeFaltasDePagina++;
				} else { // se já estiver na fila
					for (Processo p : cache) {
						if (p.nome.equals(d)) {
							Processo procurado = p;
							cache.remove(procurado);
							procurado.r = 1;
							cache.add(procurado);
							break;
						}
					}
					
				}

//	            System.out.printf("access: %d, hit: %d, misses: %d >>> %s\n[ ", access, hit, qtdDeFaltasDePagina, d);
				System.out.printf("Acessessando processo %s.\n", d);
//	            for(int i = 0; i < cache.size(); i++) {
//	            	System.out.printf("   " + cache.);
//	            }
				cache.forEach((p) -> System.out.println("  " + p.toString()));

			}
			System.out.println("Quantidade de pages fault: " + qtdDeFaltasDePagina);
		}
}
		
