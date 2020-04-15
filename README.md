# Github-Api-QrCodeScanner

O aplicativo foi separado em 3 módulos, APP, DOMAIN e qrcodecanner.

- App: Responsável pela classe de Application
- Domain: Este é o módulo responsável por fazer a comunicação com o a API, os dados populados no módulo "qrcodescanner" são oriundos das requisições realizadas pelo Domain
- qrcodecanner: Neste módulo é onde esta toda UI, feature de listagem e detalhe de detalhe de evento.

- Aplicação em um padrão de arquitetura MVVM,
- Coroutines para as operações assíncronas,
- Koin para injeção de dependências,
- Retrofit para comunicação com API,
- Foram realizados testes unitários nas ViewModel

#Funcionalidade

Para acessar os repositórios de um usuário específico é necessário gerar um QRCODE, por exemplo, https://github.com/EdmundoNeto
Qualquer formato diferente deste a aplicação irá informar uma mensagem que de é inválido.

Para tester a aplicação pode-se usar o arquivo *qrcode_edmundoneto.png* que está na raiz do projeto. Utilizando este QRCode será mostrado
usuário os repositórios presentes em: https://github.com/EdmundoNeto
