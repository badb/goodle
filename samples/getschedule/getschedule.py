#!/usr/bin/env python
# -*- coding: utf-8 -*-

import oauth2 as oauth
from datetime import datetime
import urlparse
import json
import sys

from goodle_auth import *
"""
I moved settings to other file so that no one would commit our secret credentials

"""
# USOS API Base URL, trailing slash included.
#usosapi_base_url = '';
# Consumer Key to use.
#consumer_key = '';
#consumer_secret = '';
# Access Token to use. If left blank, then user authorization process will follow.
#access_token_key = ''
#access_token_secret = ''

# End of settings. Program starts here.

if not (usosapi_base_url and consumer_key and consumer_secret):
	print "Fill the settings first."
	sys.exit(1)
usosapi_base_url_secure = usosapi_base_url.replace("http://", "https://")
consumer = oauth.Consumer(consumer_key, consumer_secret)
if access_token_key:
	access_token = oauth.Token(access_token_key, access_token_secret)
else:
	request_token_url = usosapi_base_url_secure + 'services/oauth/request_token?scopes=studies|offline_access'
	authorize_url = usosapi_base_url + 'services/oauth/authorize'
	access_token_url = usosapi_base_url_secure + 'services/oauth/access_token'
	# Step 1. Request Token
	client = oauth.Client(consumer)
	resp, content = client.request(request_token_url, "GET", callback_url='oob')
	if resp['status'] != '200':
		raise Exception("Invalid response %s:\n%s" % (resp['status'], content))
	def _read_token(content):
		arr = dict(urlparse.parse_qsl(content))
		return oauth.Token(arr['oauth_token'], arr['oauth_token_secret'])
	request_token = _read_token(content)
	# Step 2. Obtain authorization
	print "Go to the following link in your browser:"
	print "%s?oauth_token=%s" % (authorize_url, request_token.key)
	print
	oauth_verifier = raw_input('What is the PIN code? ')
	# Step 3. Access Token
	request_token.set_verifier(oauth_verifier)
	client = oauth.Client(consumer, request_token)
	resp, content = client.request(access_token_url, "GET")
	try:
		access_token = _read_token(content)
	except KeyError:
		print "Cound not retrieve Access Token (invalid PIN?)."
		sys.exit(1)

client = oauth.Client(consumer, access_token)
resp, content = client.request(usosapi_base_url + "services/tt/student?start=" +
	str(datetime.now().date()) + "&days=7&fields=start_time|end_time|name|course_id|group_number", "GET")
if resp['status'] != '200':
	raise Exception(u"Invalid response %s.\n%s" % (resp['status'], content))
items = json.loads(content)
# Print today's activities.
activities = sorted(items, lambda x, y: cmp(x['start_time'], y['start_time']))
if len(activities) > 0:
	course_id_set = set();
	for item in activities:
		course_id_set.add(item['course_id'])		
#		print u"%s - %s -- %s : %s : %s" % (item['start_time'][11:16], item['end_time'][11:16], item['name']['en'], item['course_id'], item['group_number'])
	for course_id in course_id_set:
		print u"%s" % course_id
else:
	print u"No activities today."

if not access_token_key:
	print
	print "*** You may want to hardcode these values, so you won't need to reauthorize ***"
	print "access_token_key = '%s'" % access_token.key
	print "access_token_secret = '%s'" % access_token.secret

