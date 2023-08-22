import requests
from datetime import datetime, timedelta
import folium
import webbrowser
import os

# Calculate the datetime x days ago
datetime_24_hours_ago = datetime.now() - timedelta(days=5)

# Format the datetime as a string in the format 'YYYY-MM-DDTHH:MM:SS'
datetime_str = datetime_24_hours_ago.strftime('%Y-%m-%dT%H:%M:%S')
# The API endpoint
url = 'https://data.sfgov.org/resource/wg3w-h783.json'

# Create a map centered around San Francisco
m = folium.Map(location=[37.7749, -122.4194], zoom_start=15)

# The maximum number of records to fetch per request
limit = 500

# The offset (i.e., starting point within the collection of resource items)
offset = 0
count = 0

while True:
    # The query parameters
    params = {
        '$where':  f"incident_datetime >= '{datetime_str}' AND "
#                  f"(supervisor_district = '8' OR police_district == 'Park') AND "
                   f"(incident_category = 'Assault' OR "
                   f"incident_subcategory = 'Drug Violation' OR "
                   f"incident_category = 'Burglary' OR incident_category = 'Robbery' "
                   f"OR incident_category = 'Larceny Theft')",
       '$limit': limit,
       '$offset': offset
    }

    # Send a GET request to the API endpoint
    response = requests.get(url, params=params)

    # If the request was successful, add a marker for each incident
    if response.status_code == 200:
        data = response.json()
        if not data:
            # If no more data, break the loop
            break
        for incident in data:
            count += 1
            latitude = incident.get('latitude')
            longitude = incident.get('longitude')
            incident_category = incident.get('incident_category')
            incident_subcategory = incident.get('incident_subcategory')
            incident_description = incident.get('incident_description')
            incident_time = incident.get('incident_datetime')
            datetime_object = datetime.strptime(incident_time, '%Y-%m-%dT%H:%M:%S.000')
            time_str = datetime_object.strftime('%m-%d-%Y %H:%M:%S')

            if latitude and longitude and incident_category and incident_subcategory and incident_description:
                popup_text = f"Date: {time_str}<br>Category: {incident_category}<br>Subcategory: {incident_subcategory}<br>Description: {incident_description}"
                if (incident_category == 'Assault' or incident_category == 'Robbery'):
                    color='red'
                elif (incident_category == 'Burglary'):
                    color='orange'
                elif (incident_subcategory == 'Drug Violation'):
                    color = 'pink'
                else:
                    color='blue'
                folium.Marker([float(latitude), float(longitude)], popup=folium.Popup(popup_text, max_width=250), icon=folium.Icon(color=color)).add_to(m)
        # Increase the offset by the limit for the next iteration
        offset += limit
    else:
        print(f"Request failed with status code {response.status_code}")
        break

# Save the map to an HTML file
print("Incidents: " + str(count))
map_file = 'map.html'
m.save(map_file)

# Open the map in the default web browser
webbrowser.open('file://' + os.path.realpath(map_file))
