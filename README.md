1 - Construir a imagem Docker:
docker build -t funstore .

2 - Executar o container mapeando a porta 3000 do container para a porta 3000 do host:
docker run -p 3000:3000 funstore

3 - Acessar o swagger da aplicação
http://localhost:3000/funstore/swagger-ui/index.html
