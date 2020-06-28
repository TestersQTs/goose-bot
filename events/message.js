const Guild = require("../models/guild");
const mongoose = require("mongoose");

const fs = require("fs");
const phrases = require("../goosePhrases.json");
const config = require("../gooseSettings.json");

module.exports = async (client, message) => {

  /*
  * Event logic. This doesn't need to be touched. Goose events
  * will be separated below.
  */

  Guild.findOne(
    {
      guildID: message.guild.id,
    },
    async (err, guild) => {
      const prefix = guild.guildPrefix;

      if (message.author.bot) return;
      if (!message.guild) return;
      //if (!message.content.startsWith(prefix)) return;

      if (!message.member) message.member = await message.guild.fetchMember(message);

      const args = message.content.slice(prefix.length).trim().split(/ +/g);
      const cmd = args.shift().toLowerCase();

      if (cmd.length === 0) return;

      let command = client.commands.get(cmd);
      if (!command) command = client.commands.get(client.aliases.get(cmd));

      if (command) command.run(client, message, args);

      /*
      * Pre-specified events. Might make this editable by the guild Administrators
      */
     var random = Math.random();

     console.log(random);

     if (random <= config["events"]["chatMessage"]) {
       var event = Math.floor(Math.random() * 5) + 1;

       if (event == 1) { message.channel.send(phrases["messageEvents"]["event1"]); return; }
       else if (event == 2) { message.channel.send(phrases["messageEvents"]["event2"]); return; }
       else if (event == 3) { message.channel.send(phrases["messageEvents"]["event3"]); return; }
       else if (event == 4) { message.channel.send(phrases["messageEvents"]["event4"]); return; }
       else if (event == 5) { message.channel.send(phrases["messageEvents"]["event5"]); return; }
     }
    }
  );
};
