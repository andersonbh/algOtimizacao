# Algoritmos de Otimização de Sistemas

Essa aplicação é destinada a matéria de Otimização de Sistemas da PUC Minas.

Antes de iniciar o desenvolvimento, instale e configure as seguintes dependências:

1. [Node.js][]: O Node é utilizado para rodar um servidor de desenvolvimento e fazer o build do projeto.

   Dependendo do sistema que você utilizar, o nodejs pode vir em pacotes já pré instalados.

Depois de instalar o nodeJs , execute os comandos na pasta raiz do sistema:

    npm install
    
    bower install
    
    npm install -g gulp

Crie o banco de dados para inserção dos usuários do banco e outros (PostgreSQL)

    createdb algOtimizacao 

Configure o Tomcat no Intellij IDEA e inicie a aplicação. Caso queira rodar a mesma sem o Intellij IDEA, execute:

    ./gradlew
    
Para monitorar alterações no código execute

    gulp

Para gerenciar as dependências de CSS e JavaScript, execute `bower update` ou `bower install`.

# Colocando em produção

Para colocar o sistema em produção, execute:

    ./gradlew -Pprod clean bootRepackage

Esse comando irá concatenar o CSS e os JavaScript. Para verificar se está tudo certo e rodando, execute:

    java -jar build/libs/*.war --spring.profiles.active=prod

E então, acesso [http://localhost:8080](http://localhost:8080) no browser.

# Testando

Testes unitários são executados pelo [Karma][] e escritos por [Jasmine][]. Eles estão em `src/test/javascript` e podem ser executados com o comando:

    gulp test

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
