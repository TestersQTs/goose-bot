const Guild = require("../models/guild");
const mongoose = require("mongoose");

const fs = require("fs");
const phrases = require("../goosePhrases.json");

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
     var chance = Math.floor(Math.random() * 5) + 1;
     var chanceActual = Math.floor(Math.random() * 5) + 1;
     var events = Math.floor(Math.random() * 5) + 1;

      if (chance === chanceActual && events === 1) message.channel.send(phrases["messageEvents"]["event1"]);
      if (chance === chanceActual && events === 2) message.channel.send(phrases["messageEvents"]["event2"]);
      if (chance === chanceActual && events === 3) message.channel.send(phrases["messageEvents"]["event3"]);
      if (chance === chanceActual && events === 4) message.channel.send(phrases["messageEvents"]["event4"]);
      if (chance === chanceActual && events === 5) message.channel.send(phrases["messageEvents"]["event5"]);
    }
  );
};
