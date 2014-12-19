get all the users:
echo | main.py -r

create a new user, given a json object as input
echo {"id":7, "firstName": "alex", "lastName": "brod", "password": "passs", "email": "alex@gmail.com" } | main.py -c


update a user, using a supplied json object as in input and an id as a parameter
echo {"id":7, "firstName": "ben", "lastName": "alexander", "password": "passs", "email": "ben@gmail.com" } | main.py -u -p7

delete a user
echo | main.py -d -p7





