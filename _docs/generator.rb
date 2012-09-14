# encoding: utf-8

directory = File.join(".", File.dirname(__FILE__))

require 'yaml'
require 'erb'
require File.join(directory, "commands")
require File.join(directory, "permissions")

plugin_desc = YAML.load $<.read

commands = Commands.parse plugin_desc['commands']

commands_tpl = ERB.new(
      File.read File.join(directory, "commands.html.erb"))
commands_html = commands_tpl.result(commands.get_binding)
File.open('commands.html', 'w') do |f|
  f << commands_html
end

