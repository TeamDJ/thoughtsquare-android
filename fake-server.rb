require 'rubygems'
require 'sinatra'

set :port, 2010

post '/users.json' do
    status 201
    '{"user":{"created_at":"2010-08-15T08:12:49Z","updated_at":"2010-08-15T08:12:49Z","id":18,"display_name":"Julian Oliver","email":"joliver@thoughtworks.com"}}'
end
