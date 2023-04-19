## Introdução
Projeto Final do Módulo Arquitetura de Software e Ágil I - Jornada do Conhecimento Backend Java - F1rst Tecnologia e Inovação e Ada.

## Projetos
Esse repositório contém projetos (módulos) independentes divididos em três microsserviços: albuns, eureka-server e figurinhas.

- O microsserviço de "albuns" é responsável pela criação do template de álbuns de figurinhas, pela criação de álbuns da banca e álbuns de usuários e pelo log das vendas de álbuns.
- O microsserviço de "eureka-server" é responsável pela descoberta de serviços.
- O microsserviço de "figurinhas" é responsável pela criação do template de figurinhas, pela criação de figurinhas e pelo log de venda de figurinhas entre os usuários e compra de um pacote de figurinhas da banca.
- O microsserviço de "users" é responsável pelo cadastro de usuários. Esse microsserviço encontra-se no seguinte repositório: https://github.com/AlineCruzDEV/users
- Feign foi utilizado para prover a comunicação entre os microsserviços de albuns e figurinhas.
- Usamos os padrões de projeto Decorator e Strategy (indicados no projeto).
- Aplicamos o princípio de Responsabilidade Única do SOLID.

## Equipe
Aline Cruz, Daniel Baião, Ícaro Miranda, Juliana Aquino e Leonardo Lopes.

## Diagrama
![WhatsApp Image 2023-03-25 at 14 55 04](https://user-images.githubusercontent.com/69128221/228573184-b9f8d354-6517-4848-8aa0-6df4ce4aad59.jpeg)
