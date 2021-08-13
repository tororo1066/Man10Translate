package man10translate.man10translate

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.apache.commons.lang.StringUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.*


class MT : JavaPlugin(),Listener {

    private val urllist = HashMap<UUID,String>()

    override fun onEnable() {
        saveDefaultConfig()
        server.logger.info("man10translate is enable!")

        for (list in config.getStringList("urllist")){
            val uuid = list.split(":")[0]
            val url = list.split(":")[1]

            urllist[UUID.fromString(uuid)] = url
        }

    }


    private fun Player.translate(langTo: String, text: String){

        try {
            val urlapi : String = if (urllist.containsKey(this.uniqueId)){
                urllist[this.uniqueId]!!
            }else{
                "https://script.google.com/macros/s/AKfycbyiP4X1LV7zvW_jJwglyVs5bkMb9mwXM6H4ahvbEAWy7VC3ZiF6/exec"
            }
            val strEnc = URLEncoder.encode(text,"UTF-8")
            val content = StringBuilder()

            val url = URL("$urlapi?text=$strEnc&target=$langTo")
            val urlConnection = url.openConnection()
            urlConnection.setRequestProperty("User-Agent", "mtranslate")
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream(),"UTF-8"))
            val line = bufferedReader.readLine()
            content.append(line)
            val contentstring = content.toString().substring(20..content.toString().length-3)

            if (contentstring.contains("実行した回数が多すぎます。")){
                this.sendMessage("§c翻訳数が上限に達しています。")
                return
            }


            val comptext = Component.text("§6[翻訳] §a➜§f $contentstring").clickEvent(ClickEvent.copyToClipboard(contentstring)).hoverEvent(HoverEvent.showText(
                Component.text("§6Click to copy!")))
            this.sendMessage(comptext)
            return
        }catch (e : Exception){
            e.printStackTrace()
        }
    }




    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player)return true
        when(label){
            "mtranslate"->{
                sender.translate(sender.locale().language,StringUtils.join(args," "))
                return true
            }
            "mtranslateconfig"->{
                if (args.isEmpty()){
                    if (!urllist.containsKey(sender.uniqueId))return true
                    val conlist = config.getStringList("urllist")
                    conlist.remove(sender.uniqueId.toString() + ":" + urllist[sender.uniqueId])
                    urllist.remove(sender.uniqueId)
                    config.set("urllist",conlist)
                    saveConfig()
                    sender.sendMessage("削除しました")
                    return true
                }

                if (args.size != 1){
                    sender.sendMessage("/mtranslateconfig [URL]")
                    return true
                }
                try {
                    URL(args[0])
                }catch (e : MalformedURLException){
                    sender.sendMessage("urlを指定してください")
                    return true
                }
                val removeconfig = sender.uniqueId.toString() + ":" + urllist[sender.uniqueId]
                val addconfig = sender.uniqueId.toString() + ":" + args[0]
                val conlist = config.getStringList("urllist")
                conlist.remove(removeconfig)
                conlist.add(addconfig)
                urllist.remove(sender.uniqueId)
                urllist[sender.uniqueId] = args[0]
                config.set("urllist",conlist)
                saveConfig()
                sender.sendMessage("保存しました")
                return true
            }
        }
        return true
    }



}