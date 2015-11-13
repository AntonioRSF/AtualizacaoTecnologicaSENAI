CREATE DATABASE licitanetdb;

USE licitanetdb;

CREATE TABLE usuario (
  id INT AUTO_INCREMENT ,
  login VARCHAR(45) NOT NULL ,
  senha VARCHAR(45) NOT NULL ,
  nome VARCHAR(100) ,
  cpf VARCHAR(14) ,
  rg VARCHAR(45) ,
  email VARCHAR(100) NOT NULL ,
  telefone VARCHAR(45) NOT NULL ,
  razaoSocial VARCHAR(100) ,
  nomeFantasia VARCHAR(100) ,
  cnpj VARCHAR(18) ,
  ie VARCHAR(45) ,
  tipo VARCHAR(45) NOT NULL ,
  ativo BOOLEAN NOT NULL ,
  PRIMARY KEY (id) ,
  UNIQUE KEY (login)
);

CREATE TABLE usuario_permissao(
  idUsuario INT NOT NULL,
  permissao VARCHAR(45),
  PRIMARY KEY (idUsuario) ,
  FOREIGN KEY (idUsuario) REFERENCES usuario (id)
);

CREATE  TABLE produto (
  id INT AUTO_INCREMENT ,
  nome VARCHAR(200) NOT NULL ,
  detalhes VARCHAR(500) NOT NULL ,
  PRIMARY KEY (id)
);

CREATE  TABLE oferta (
  id INT AUTO_INCREMENT ,
  quantidade INT NOT NULL ,
  dataInicio DATETIME NOT NULL ,
  dataFim DATETIME NOT NULL ,
  descricao VARCHAR(200) NOT NULL ,
  idProduto INT NOT NULL,
  idUsuarioVencedor INT,
  status INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idUsuarioVencedor) REFERENCES Usuario (id),
  FOREIGN KEY (idProduto) REFERENCES produto (id)
);

CREATE TABLE lance (
  id INT AUTO_INCREMENT ,
  valor DECIMAL(10,2) NOT NULL ,
  data DATETIME NOT NULL ,
  flVencedor BOOLEAN NOT NULL ,
  idOferta INT,
  idUsuario INT,
  PRIMARY KEY (id) ,
  FOREIGN KEY (idOferta) REFERENCES Oferta (id) ,
  FOREIGN KEY (idUsuario) REFERENCES usuario (id)
);

INSERT INTO usuario 
(id,login   ,senha,nome                    ,cpf             ,rg         ,email                      ,telefone        ,razaoSocial,nomeFantasia,cnpj  ,ie    ,tipo,ativo) VALUES
(1 ,'joao','123','João da Silva','000.000.000-00','1.111.111','joaosilva@gmail.com','(48)0000-0000','null'     ,'null'      ,'null','null','PF',true )
;
INSERT INTO usuario 
(id,login  ,senha,nome                  ,cpf             ,rg         ,email                   ,telefone        ,razaoSocial,nomeFantasia,cnpj  ,ie    ,tipo,ativo) VALUES
(2 ,'nildo','123','Nildo Beppler Junior','000.000.000-00','1.111.111','nildobeppler@gmail.com','(48) 0000-0000','null'     ,'null'      ,'null','null','PF',true )
;

INSERT INTO usuario_permissao 
(idUsuario,permissao           ) VALUES
(1        ,'ROLE_LANCADOR')
;
INSERT INTO usuario_permissao 
(idUsuario,permissao           ) VALUES
(2        ,'ROLE_ADMINISTRADOR')
;

INSERT INTO produto VALUES ( 1,'Mini Modem 3G','Modem 3G da Olivetti')
;



INSERT INTO produto VALUES
(
   2,
   'Notebook Sony VAIO VPCEH30EB/W',
   'Intel Core I3-2350M, 4GB, HD 500GB, Win7HB, 15.6'
)
;

INSERT INTO produto VALUES
(
   3,
   'Ultrabook Acer S3-951-6636',
   'Intel Core i3 2367M, 4GB, SSD 240GB, Win7HB - 13.3 LED'
)
;
INSERT INTO produto VALUES
(
   4,
   'Notebook ASUS X44C-VX004R',
   'Intel Core I3 2330M, 4GB, HD 500GB, DVD+RW, Win7HB, 14 LED'
)
;
INSERT INTO produto VALUES
(
   5,'GPS Foston','Navegador FS-700DT Touch Screen 7 Bluetooth, TV Digital'
)
;

