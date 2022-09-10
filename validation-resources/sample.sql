with service_response as (
	select
		http_get(
			'http://0.0.0.0:8000/list.json',
			map_from_entries(
				ARRAY[
					('first', 'first value'),
					('number', '3')
				]
			),
			map_from_entries(
				ARRAY[
				('AUTHORIZARION', 'BASIC hjsdfysdiaofyauisoy')
				]
			)
		) as api_response
),
converted as (
	SELECT
		cast(api_response AS ARRAY(JSON)) as api_response
	FROM
		service_response
),
parsed as (
	SELECT
		json_format(response) as response
	from
		converted
			CROSS JOIN UNNEST (api_response) as t(response)
)

select
	json_query(response, 'strict $.cliet_uid') as client_uid,
	json_query(response, 'strict $.requester_uid') as requester_uid,
	json_query(response, 'strict $.size.double()') as size,
	json_query(response, 'strict $.floating_point.double()') as floating_point
from
	parsed