class Commands

  attr :categories
  
  def self.parse(yml)
    categories_hash = {}
    
    yml.each do |command_yml|
      category = command_yml[1]['category']
      category = [ category ] unless category.kind_of? Array
      category.compact!
      next if category.empty?
      
      command = Command.new(*command_yml)
      categories_hash[category] ||= Category.new category
      categories_hash[category] << command
    end
    new categories_hash.values
  end

  def initialize(categories)
    @categories = categories
  end
  
  def get_binding
    binding
  end
end

class Category
  
  attr_reader :name, :commands

  def initialize(names)
    @name = names * " "
    @commands = []
  end

  def class_name
    @name.downcase.gsub(" ", "-")
  end
  
  def <<(command)
    @commands << command
  end
end
    
class Command
  
  attr_reader :name, :description, :permission, :aliases
  
  include ERB::Util
  
  def initialize(name, info)
    @name = h name
    @description = h info['description']
    @usage = h info['usage']
    @permission = h info['permission']
    aliases = info['aliases']
    if aliases.nil?
      @aliases = []
      else
      @aliases = aliases.map { |_alias| h _alias }
    end
  end
  
  def usage
    "/#@name #@usage"
  end

  def has_aliases?
    @aliases.size > 0
  end
  
  def has_only_one_alias?
    @aliases.size == 1
  end
end
