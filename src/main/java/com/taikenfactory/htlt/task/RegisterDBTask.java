package com.taikenfactory.htlt.task;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taikenfactory.htlt.domain.DomesticDGA;
import com.taikenfactory.htlt.domain.DomesticDGARepository;
import com.taikenfactory.htlt.domain.DomesticDP;
import com.taikenfactory.htlt.domain.DomesticDPRepository;
import com.taikenfactory.htlt.domain.DomesticMGA;
import com.taikenfactory.htlt.domain.DomesticMGARepository;
import com.taikenfactory.htlt.domain.Mesh;
import com.taikenfactory.htlt.domain.MeshRepository;

@Component
public class RegisterDBTask {
	private static final String APIKEY = "API KEY for Docomo";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>(){};
	
	private final MeshRepository meshRepository;
	private final DomesticMGARepository domesticMGARepository;
	private final DomesticDGARepository domesticDGARepository;
	private final DomesticDPRepository domesticDPRepository;
	
	@Autowired
	public RegisterDBTask(MeshRepository meshRepository, DomesticMGARepository domesticMGARepository, DomesticDGARepository domesticDGARepository, DomesticDPRepository domesticDPRepository) {
		this.meshRepository = meshRepository;
		this.domesticMGARepository = domesticMGARepository;
		this.domesticDGARepository = domesticDGARepository;
		this.domesticDPRepository = domesticDPRepository;
	}
	
	private String convertTimeStr(String timeStr) {
		if (timeStr.startsWith("00")) return "41";
		else if (timeStr.startsWith("05")) return "42";
		else if (timeStr.startsWith("10")) return "43";
		else if (timeStr.startsWith("14")) return "44";
		else if (timeStr.startsWith("18")) return "45";
		else if (timeStr.startsWith("21")) return "46";
		else return "";
	}
	
	private void insertRawDatas() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		// メッシュ情報
		Map<String, Mesh> meshMap = new HashMap<String, Mesh>();
		for (String code1 : Arrays.asList("513570", "513571", "523500", "523501", "523502", "523511", "523512")) {
			for (int code2 = 0; code2 < 100; code2++) {
				for (int code3 = 1; code3 <= 4; code3++) {
					String code = String.format("%s%02d%d", code1, code2, code3);
					Mesh mesh = new Mesh(code);
					mesh = meshRepository.save(mesh);
					meshMap.put(code, mesh);
				}
			}
		}

		// 国内ユーザ 月ー時間帯別（性別・年代あり）
//		{
//			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//			meshMap.keySet().stream()
//				.forEach(codeStr -> {
//					Stream.of("201505")
//						.forEach(monthStr -> {
//							Date date;
//							try {
//								date = sdf.parse(monthStr);
//							}
//							catch (Exception e) {
//								e.printStackTrace();
//								return;
//							}
//
//							Stream.of("01", "02")
//								.forEach(weekdayStr -> {
//									Stream.of("41", "42", "43", "44", "45", "46")
//									.forEach(timeStr -> {
//										String urlStr = String.format("https://api.apigw.smt.docomo.ne.jp/pop/v1/domestic_mga?APIKEY=%s&code=%s&month=%s&weekday=%s&time=%s", APIKEY, codeStr, monthStr, weekdayStr, timeStr);
//										Map<String, Object> data, result;
//										System.out.println(urlStr);
//										HttpGet httpGet = new HttpGet(urlStr);
//										try {
//											HttpResponse httpResponse = httpClient.execute(httpGet);
//											data = objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity(), "utf-8"), typeReference);
//											result = (Map<String, Object>) (((List<Object>)(data.get("result"))).get(0));
//										}
//										catch(Exception e) {
//											e.printStackTrace();
//											return;
//										}
//
//										final Map<String, Object> resultFinal = result;
//										Stream.of("male", "female")
//											.flatMap(genderStr -> {
//												return ((Map<String, Object>) (resultFinal.get(genderStr))).entrySet().stream()
//													.map(entry -> {
//														try {
//															DomesticMGA domesticMGA = new DomesticMGA(meshMap.get(codeStr), date, weekdayStr, timeStr, genderStr, entry.getKey(), (int)entry.getValue());
//															return domesticMGA;
//														}
//														catch(Exception e) {
//															e.printStackTrace();
//															return null;
//														}
//													})
//													.filter(domesticMGA -> domesticMGA != null)
//													.collect(Collectors.toList()).stream();
//											})
//											.forEach(domesticMGA -> {
//												domesticMGARepository.save(domesticMGA);
//											});
//									});
//								});
//						});
//				});
//		}
		
//		List<String> dateStrs = Arrays.asList("20150501", "20150502", "20150503", "20150504", "20150505", "20150506", "20150507", "20150508", "20150509", "20150510");
//		List<String> dateStrs = Arrays.asList("20140802", "20140803", "20140809", "20140810", "20150502", "20150503", "20150504", "20150505", "20150506");
		List<String> dateStrs = Arrays.asList(
			"20140503", "20140504", "20140505", "20140506", "20140510", "20140511", "20140517", "20140518", "20140524", "20140525", "20140531",
			"20140601", "20140607", "20140608", "20140614", "20140615", "20140621", "20140622", "20140628", "20140629",
			"20140705", "20140706", "20140712", "20140713", "20140719", "20140720", "20140721", "20140726", "20140727",
			"20140802", "20140803", "20140809", "20140810", "20140816", "20140817", "20140823", "20140824", "20140830", "20140831",
			"20140906", "20140907", "20140913", "20140914", "20140915", "20140920", "20140921", "20140923", "20140927", "20140928",
			"20141004", "20141005", "20141011", "20141012", "20141013", "20141018", "20141019", "20141025", "20141026",
			"20141101", "20141102", "20141103", "20141108", "20141109", "20141115", "20141116", "20141122", "20141123", "20141124", "20141129", "20141130",
			"20141206", "20141207", "20141213", "20141214", "20141220", "20141221", "20141223", "20141227", "20141228",
			"20150101", "20150103", "20150104", "20150110", "20150111", "20150112", "20150117", "20150118", "20150124", "20150125", "20150131",
			"20150201", "20150207", "20150208", "20150211", "20150214", "20150215", "20150221", "20150222", "20150228",
			"20150301", "20150307", "20150308", "20150314", "20150315", "20150321", "20150322", "20150328", "20150329",
			"20150404", "20150405", "20150411", "20150412", "20150418", "20150419", "20150425", "20150426", "20150429",
			"20150502", "20150503", "20150504", "20150505", "20150506", "20150509", "20150510", "20150516", "20150517", "20150523", "20150524", "20150530", "20150531");

		// 国内ユーザ 特定日ー時間帯別（居住地あり）
//		{
//			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			meshMap.keySet().stream()
//				.forEach(codeStr -> {
//					dateStrs.stream()
//						.forEach(dateStr -> {
//							Date date;
//							try {
//								date = sdf.parse(dateStr);
//							}
//							catch (Exception e) {
//								e.printStackTrace();
//								return;
//							}
//
//							Stream.of("41", "42", "43", "44", "45", "46")
//								.forEach(timeStr -> {
//									String urlStr = String.format("https://api.apigw.smt.docomo.ne.jp/pop/v1/domestic_dga?APIKEY=%s&code=%s&date=%s&time=%s", APIKEY, codeStr, dateStr, timeStr);
//									Map<String, Object> data, result;
//									System.out.println(urlStr);
//									HttpGet httpGet = new HttpGet(urlStr);
//									try {
//										HttpResponse httpResponse = httpClient.execute(httpGet);
//										data = objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity(), "utf-8"), typeReference);
//										result = (Map<String, Object>) (((List<Object>)(data.get("result"))).get(0));
//									}
//									catch(Exception e) {
//										e.printStackTrace();
//										return;
//									}
//
//									final Map<String, Object> resultFinal = result;
//									Stream.of("male", "female")
//										.flatMap(genderStr -> {
//											return ((Map<String, Object>) (resultFinal.get(genderStr))).entrySet().stream()
//												.map(entry -> {
//													try {
//														DomesticDGA domesticDGA = new DomesticDGA(meshMap.get(codeStr), date, timeStr, genderStr, entry.getKey(), (int)entry.getValue());
//														return domesticDGA;
//													}
//													catch(Exception e) {
//														e.printStackTrace();
//														return null;
//													}
//												})
//												.filter(domesticDGA -> domesticDGA != null)
//												.collect(Collectors.toList()).stream();
//										})
//										.forEach(domesticDGA -> {
//											domesticDGARepository.save(domesticDGA);
//										});
//								});
//						});
//				});
//		}

		// 国内ユーザ 特定日ー時間帯別（居住地あり）
		{
			final List<String> prefStrs = IntStream.range(1, 48).mapToObj(item -> String.format("%02d", item)).collect(Collectors.toList());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			meshMap.keySet().stream()
				.forEach(codeStr -> {
					dateStrs.stream()
						.forEach(dateStr -> {
							Date date;
							try {
								date = sdf.parse(dateStr);
							}
							catch (Exception e) {
								e.printStackTrace();
								return;
							}
							
//							String urlStr = String.format("https://api.apigw.smt.docomo.ne.jp/pop/v1/domestic_dp?APIKEY=%s&code=%s&date=%s&pref=%s", APIKEY, codeStr, dateStr, String.join(",", prefStrs));
							String urlStr = String.format("https://api.apigw.smt.docomo.ne.jp/mss/v1/domestic_dp?APIKEY=%s&code=%s&date=%s&pref=%s", APIKEY, codeStr, dateStr, String.join(",", prefStrs));
							Map<String, Object> data, result;
							System.out.println(urlStr);
							HttpGet httpGet = new HttpGet(urlStr);
							try {
								HttpResponse httpResponse = httpClient.execute(httpGet);
								data = objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity(), "utf-8"), typeReference);
								result = (Map<String, Object>) (((List<Object>)(data.get("result"))).get(0));
							}
							catch(Exception e) {
								e.printStackTrace();
								return;
							}
							
							final Map<String, Object> resultFinal = result;
							prefStrs.stream()
								.flatMap(prefStr -> {
									if (!resultFinal.containsKey(prefStr)) {
										return Stream.empty();
									}
									else {
										return ((Map<String, Object>) (resultFinal.get(prefStr))).entrySet().stream()
												.map(entry -> {
													try {
														DomesticDP domesticDP = new DomesticDP(meshMap.get(codeStr), date, convertTimeStr(entry.getKey()), prefStr, (int)entry.getValue());
														return domesticDP;
													}
													catch(Exception e) {
														e.printStackTrace();
														return null;
													}
												})
												.filter(domesticDP -> domesticDP != null)
												.collect(Collectors.toList()).stream();
									}
								})
								.forEach(domesticDP -> {
									domesticDPRepository.save(domesticDP);
								});
						});
				});
		}
		
		System.out.println(meshRepository.findAll().size());
		System.out.println(domesticMGARepository.findAll().size());
		System.out.println(domesticDGARepository.findAll().size());
		System.out.println(domesticDPRepository.findAll().size());
	}
	
	private void insertSubTotals() {
		// 個々のメッシュの小計値を挿入
		domesticMGARepository.getSumsByMeshMonthWeekdayTime().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				domesticMGARepository.save(new DomesticMGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], (String) objects[3], null, null, (int)(long) objects[4]));
			});
		domesticMGARepository.getSumsByMeshMonthWeekdayTimeGender().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				if (objects[4] == null) return;
				domesticMGARepository.save(new DomesticMGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], (String) objects[3], (String) objects[4], null, (int)(long) objects[5]));
			});
		domesticMGARepository.getSumsByMeshMonthWeekdayTimeAge().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				if (objects[4] == null) return;
				domesticMGARepository.save(new DomesticMGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], (String) objects[3], null, (String) objects[4], (int)(long) objects[5]));
			});
		System.out.println(domesticMGARepository.findAll().size());
		
		domesticDGARepository.getSumsByMeshDateTime().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				domesticDGARepository.save(new DomesticDGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], null, null, (int)(long) objects[3]));
			});
		domesticDGARepository.getSumsByMeshDateTimeGender().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				if (objects[3] == null) return;
				domesticDGARepository.save(new DomesticDGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], (String) objects[3], null, (int)(long) objects[4]));
			});
		domesticDGARepository.getSumsByMeshDateTimeAge().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				if (objects[3] == null) return;
				domesticDGARepository.save(new DomesticDGA((Mesh) objects[0], (Date) objects[1], (String) objects[2], null, (String) objects[3], (int)(long) objects[4]));
			});
		System.out.println(domesticDGARepository.findAll().size());
		
		domesticDPRepository.getSumsByMeshDateTime().stream()
			.forEach(object -> {
				Object[] objects = (Object[]) object;
				domesticDPRepository.save(new DomesticDP((Mesh) objects[0], (Date) objects[1], (String) objects[2], null, (int)(long) objects[3]));
			});
		System.out.println(domesticDPRepository.findAll().size());

		// 全メッシュの平均値を挿入
		// 全メッシュ平均のメッシュコードを 0 とする
		{
			Mesh mesh = meshRepository.findByCode("0").orElseGet(() -> {
				Mesh meshLocal = new Mesh("0");
				meshLocal = meshRepository.save(meshLocal);
				return meshLocal;
			});
			
			domesticDGARepository.getSumsByDateTime().stream()
				.forEach(object -> {
					Object[] objects = (Object[]) object;
					domesticDGARepository.save(new DomesticDGA(mesh, (Date) objects[0], (String) objects[1], null, null, (int)(long) objects[2]));
				});
			domesticDGARepository.getSumsByDateTimeGender().stream()
				.forEach(object -> {
					Object[] objects = (Object[]) object;
					domesticDGARepository.save(new DomesticDGA(mesh, (Date) objects[0], (String) objects[1], (String) objects[2], null, (int)(long) objects[3]));
				});
			domesticDGARepository.getSumsByDateTimeAge().stream()
				.forEach(object -> {
					Object[] objects = (Object[]) object;
					domesticDGARepository.save(new DomesticDGA(mesh, (Date) objects[0], (String) objects[1], null, (String) objects[2], (int)(long) objects[3]));
				});
			System.out.println(domesticDGARepository.findAll().size());
			
			domesticDPRepository.getSumsByDateTime().stream()
				.forEach(object -> {
					Object[] objects = (Object[]) object;
					domesticDPRepository.save(new DomesticDP(mesh, (Date) objects[0], (String) objects[1], null, (int)(long) objects[2]));
				});
			domesticDPRepository.getSumsByDateTimePrefecture().stream()
				.forEach(object -> {
					Object[] objects = (Object[]) object;
					domesticDPRepository.save(new DomesticDP(mesh, (Date) objects[0], (String) objects[1], (String) objects[2], (int)(long) objects[3]));
				});
			System.out.println(domesticDPRepository.findAll().size());
		}
	}
	
	private void test() {
//		domesticDGARepository.getHotRatios("2015-05-10", "45", "2015-05-01", "02").stream()
//			.forEach(object -> {
//				Object[] objects = (Object[])object;
//				System.out.println(String.format("%s %s %s %d %d", objects[0], objects[1], objects[2], objects[3], objects[4]));
//			});
	}

	public void execute() {
//		insertRawDatas();
//		insertSubTotals();
//		test();
	}

}