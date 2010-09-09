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

get '/users.json' do
    "["+
    "{'user':{'created_at':'2010-08-15T08:12:49Z'," +
      "'updated_at':'2010-08-15T08:12:49Z',"+
      "'id':18,'display_name':'duana',"+
     "'email':'foo@gmail.com'," + 
     "'mobile_number':'0421757702' }},"+
    "{'user':{'created_at':'2010-08-15T08:12:49Z'," +
      "'updated_at':'2010-08-15T08:12:49Z',"+
      "'id':18,'display_name':'james',"+
     "'email':'bar@gmail.com'," + 
     "'mobile_number':'1757702' }}"+
     "]"
end


get '/events.json' do
    date = Time.now.getutc.strftime("%Y-%m-%dT%H:%M:%SZ")

    '[{"event":{"description":"Framber has arrived in Brisbane","title":"new smurf on your turf","user_id":298486374,"location_id":113629430,"when":"'+ date +'","event_type":"arrive"}}]'
end

get '/locations.json' do
   '[{"location":{"latitude":1.5,"created_at":"2010-09-05T08:05:00Z","title":"three","updated_at":"2010-09-05T08:05:00Z","id":113629430,"longitude":1.5}},{"location":{"latitude":1.5,"created_at":"2010-09-05T08:05:00Z","title":"two","updated_at":"2010-09-05T08:05:00Z","id":298486374,"longitude":1.5}},{"location":{"latitude":1.5,"created_at":"2010-09-05T08:05:00Z","title":"one","updated_at":"2010-09-05T08:05:00Z","id":980190962,"longitude":1.5}},{"location":{"latitude":454.0,"created_at":"2010-09-09T11:51:34Z","title":"dsfdf","updated_at":"2010-09-09T11:51:34Z","id":980190963,"longitude":55565.0}}]' 
end
