const mongoose = require("mongoose");

const Guild = require("../models/guild");
const Config = require("../models/config");

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

      /*
       * Pre-specified events. Might make this editable by the guild Administrators
       */

      Config.findOne(
        {
          guildID: message.guild.id,
        },
        async (err, res) => {
          if (Math.random() <= Number(res.eventMessageProbability)) {
            var event = Math.floor(Math.random() * phrases["messageEvents"]["eventMessage"].length);
            message.channel.send(phrases["messageEvents"]["eventMessage"][event]);
          }
        }
      );

      const args = message.content.slice(prefix.length).trim().split(/ +/g);
      const cmd = args.shift().toLowerCase();

      if (cmd.length === 0) return;

      let command = client.commands.get(cmd);
      if (!command) command = client.commands.get(client.aliases.get(cmd));

      if (command) command.run(client, message, args);
    }
  );
};
