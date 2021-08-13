
# このプラグインについて
 
このプラグインは翻訳ができるプラグインです  
Google Apps Scriptを使用してます
 
 
# 翻訳ができなくなってしまったら
 
1.まず[GoogleDrive](https://drive.google.com/drive/my-drive?hl=ja)にアクセスします(ログイン必須)  
2.左の新規をクリックします  
<img src="https://user-images.githubusercontent.com/71592738/129329230-3dd1025d-73f9-43df-b452-7b803a0c3844.png" width="200px">  
3.その他からGoogle Apps Scriptを選択します  
<img src="https://user-images.githubusercontent.com/71592738/129330077-b267b27c-78a8-4aca-98b4-513ac010c816.png" width="400px">  
4.この画面にこれをコピペします  
<img src="https://user-images.githubusercontent.com/71592738/129330494-c8b4b0ba-ea6c-44f9-953b-7fbc13ae1c34.png" width="400px">  
```
function doGet(e)
{
 var p = e.parameter;
 var translatedText = LanguageApp.translate(p.text, "", p.target);
 return ContentService.createTextOutput(translatedText);
}
```  
5.【公開】を選択し、ウェブアプリケーションとして導入を選択します 名前は適当でいいです   
<img src="https://user-images.githubusercontent.com/71592738/129331280-355380be-2c45-4b6e-a914-01914dfcf47e.png" width="300px">  
6.このように設定し、更新を押します  
<img src="https://user-images.githubusercontent.com/71592738/129331810-d04e05b6-1e8c-422a-829d-f569bb37a66a.png" width="300px">  
7.このURLをコピーし、**サーバーで**/mtranslateconfig 【URL】と入力します  
<img src="https://user-images.githubusercontent.com/71592738/129332171-4823ed4a-e5ae-42f9-b46d-de4553920283.png" width="300px">  
これで完成です
