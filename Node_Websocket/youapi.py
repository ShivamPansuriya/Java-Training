import googleapiclient.discovery

youtube = googleapiclient.discovery.build("youtube", "v3", developerKey="API_KEY")

request = youtube.subscriptions().list(
    part="snippet,contentDetails",
    channelId="UCt5USYpzzMCYhkirVQGHwKQ"
)
response = request.execute()
print(response)