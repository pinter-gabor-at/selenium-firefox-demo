from marionette_driver.marionette import Marionette

client = Marionette('127.0.0.1', port=2828)
client.start_session()

original_window = client.current_window_handle
for handle in client.window_handles:
    if handle != original_window:
        client.switch_to_window(handle)
        print("Switched to window with '{}' loaded.".format(client.get_url()))
client.switch_to_window(original_window)

url = 'https://pintergabor.eu'
client.navigate(url)
