require 'rubygems'
require 'sinatra'

set :port, 2010

post '/users.json' do
    puts params.inspect
    display_name = params['user']['display_name'] 
    email = params['user']['email'] 
    status 201
   "{'user':{'created_at':'2010-08-15T08:12:49Z'," +
            "'updated_at':'2010-08-15T08:12:49Z',"+
            "'id':18,'display_name':'#{display_name}',"+
            "'email':'#{email}'}" + 
   "}"
end

get '/events.json' do
    '[{"event":{"description":"MyString has arrived in three","title":"new smurf on your turf","user_id":298486374,"location_id":113629430,"when":"2010-09-05T08:12:12Z","event_type":"arrive"}}]'
end

