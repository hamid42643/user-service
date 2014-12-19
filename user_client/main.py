import requests
import json
import sys, getopt
import ast

class UserClient:
  url = "http://localhost:8083/myapp/myresource/users/"
  headers = {'content-type': 'application/json'} 
  #create new user
  def createUser(self, payload):
    r = requests.post(self.url, data=json.dumps(ast.literal_eval(payload)), headers=self.headers)
    print r
  #update a user
  def updateUser(self, userId, payload):
    print self.url+userId 
    r = requests.put(self.url+userId, data=json.dumps(ast.literal_eval(payload)), headers=self.headers)
    print r
  #delete a user
  def delUser(self, userId):
    r = requests.delete(self.url+userId, headers=self.headers)
    print r
  def getUsers(self):
    #get all users
    r = requests.get(self.url)
    s = json.loads(r.text)
    for users in s["user"]:
      print "------------"
      for key, value in users.iteritems():
        print str(key)+": "+str(value)



def main(argv):
   jsonPayload=sys.stdin.read()
   client = UserClient()
   param = ''
   operation =''
   try:
      opts, args = getopt.getopt(argv,"hcrudp:",["pfile="])
   except getopt.GetoptError:
      print 'test.py [-c -r -u -d] -p:<url parameter> -j:<json payload>'
      sys.exit(2)
   for opt, arg in opts:
      if opt == '-h':
         print 'test.py [-c -r -u -d] -p:<url parameter>'
         sys.exit()
      elif opt in ("-p", "--pfile"):
         param = arg
      elif opt == '-c':
         operation = 'create'
      elif opt == '-r':
         operation = 'getall'      
      elif opt == '-u':
         operation = 'update'
      elif opt == '-d':
         operation = 'delete'
   if operation == 'create':
      client.createUser(jsonPayload)
   elif operation == 'getall':
      client.getUsers() 
   elif operation == 'update':
      print param
      client.updateUser(param, jsonPayload)
   elif operation == 'delete':
      client.delUser(param) 
if __name__ == "__main__":
   main(sys.argv[1:])










