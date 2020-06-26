const Discord = require("discord.js");

module.exports = {
  name: "ping",
  category: "info",
  description: "Returns bot and API latency in milliseconds.",
  run: async (client, message, args) => {
    const embed = new Discord.MessageEmbed()
      .setColor(process.env.COLOR)
      .setTitle("🏓 Pong!")
      .setDescription(`Bot Latency is **${Math.floor(new Date().getTime() - message.createdTimestamp)} ms** \nAPI Latency is **${Math.round(client.ws.ping)} ms**\nWebsocket Latency is **${Math.round(message.guild.shard.ping)} ms**`);

    message.channel.send(embed);
  },
};
