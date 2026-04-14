# **Aluguel de veículos**

## **1. Descrição**
A aplicação foi feita inteiramente em linguagem JAVA e suas classes principais são:
- MeiosDeTransporte (Classe Abstrata)
- MeiosDeTransporteOutputStream (Realiza operações de read e write em três formas de fluxo)
- MeiosDeTransporteInputStream 
- CarroDePasseio
- Moto
- Caminhao
- Onibus
- LocadoraDeVeiculos (Classe de agregação)

A aplicação roda em um servidor que recebe requisições de um cliente.
Basicamente o servidor executa uma de 4 ações:
- 1. Listar veículos => Lista todos os veiculos, tanto os disponíveis como o alugado
- 2. Alugar veículo => Lista todos os veiculos disponiveis. A seleção é feita digitando o número correspondente ao id do veículo desejado
- 3. Devolver veículo => O cliente devolve o veículo
- 4. Limpar a tela => Limpa o texto deixado no terminal

## **2. Como Usar**
Basta rodar em um computador o arquivo "project/src/project/TCPServer.java" em um computador (ele será o servidor), e em um outro computador rodar o arquivo
"project/src/project/TCPClient.java". **Obs.:** O computador que rodará o lado cliente precisará ter na mesma pasta do TCPClient.java os arquivos de implementação
das classes: MeiosDeTransporte, CarroDePasseio, Moto, Caminhao, Onibus.
