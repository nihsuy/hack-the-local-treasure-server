package com.taikenfactory.htlt.web;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taikenfactory.htlt.domain.DomesticDGARepository;
import com.taikenfactory.htlt.domain.DomesticDPRepository;
import com.taikenfactory.htlt.domain.HotAreaDTO;
import com.taikenfactory.htlt.domain.MeshRepository;

@RestController
@RequestMapping(value = "htlt")
public class HotAreaRestController {
	private static final Logger logger = LoggerFactory.getLogger(HotAreaRestController.class);
	
	private final MeshRepository meshRepository;
	private final DomesticDGARepository domesticDGARepository;
	private final DomesticDPRepository domesticDPRepository;
	
	@Autowired
	public HotAreaRestController(MeshRepository meshRepository, DomesticDGARepository domesticDGARepository, DomesticDPRepository domesticDPRepository) {
		this.meshRepository = meshRepository;
		this.domesticDGARepository = domesticDGARepository;
		this.domesticDPRepository = domesticDPRepository;
	}
	
	@RequestMapping(value = "/temporal", method = RequestMethod.GET)
	public Collection<HotAreaDTO> getByTemporal(@RequestParam("touristtype") String touristType) throws Exception {
		if (touristType.equals("all")) {
			// TODO
			return null;
		}
		else {
			// TODO
			return null;
		}
	}

	@RequestMapping(value = "/spacial", method = RequestMethod.GET)
	public Collection<HotAreaDTO> getBySpacial(@RequestParam("touristtype") String touristType) throws Exception {
		double ratioBase;
		List<Object> results;

		if (touristType.equals("all")) {
			return null;
		}
		else if (touristType.equals("male") || touristType.equals("female") || touristType.equals("20") || touristType.equals("30") || touristType.equals("40") || touristType.equals("50") || touristType.equals("60") || touristType.equals("70")) {
			if (touristType.equals("male") || touristType.equals("female")) {
				results = domesticDGARepository.getHotRatiosByGender(touristType);
			}
			else {
				results = domesticDGARepository.getHotRatiosByAge(touristType);
			}
			{
				Object[] objects = (Object[]) results.remove(0);
				String meshCode = (String) objects[0];
				int numOfTarget = (int) objects[1];
				int numOfAll = (int) objects[2];
				int numOfLocalNight = (int) objects[3];
				
//				System.out.println(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight));
				
				ratioBase = (double) numOfTarget / (double) numOfAll;
			}
			return results.stream()
				.map(object -> {
					Object[] objects = (Object[]) object;
					String meshCode = (String) objects[0];
					int numOfTarget = (int) objects[1];
					int numOfAll = (int) objects[2];
					int numOfLocalNight = (int) objects[3];
					double ratio;

//					System.out.println(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight));
					
					if (numOfAll >= numOfLocalNight + 75) {
						ratio = (double) numOfTarget / (double) numOfAll;
						ratio = ratio / ratioBase;
						return new HotAreaDTO(meshCode, ratio);
					}
					else {
						return null;
					}
				})
				.filter(dto -> dto != null)
				.collect(Collectors.toList());
		}
		
		else if (touristType.equals("local") || touristType.equals("nonlocal")) {
			results = domesticDPRepository.getHotRatiosByLocal();
			{
				Object[] objects = (Object[]) results.remove(0);
				String meshCode = (String) objects[0];
				int numOfTarget = (int) objects[1];
				int numOfAll = (int) objects[2];
				int numOfLocalNight = (int) objects[3];
				
				System.out.println(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight));
//				logger.debug(String.format(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight)));
				
				ratioBase = (double) (numOfTarget - numOfLocalNight) / (double) (numOfAll - numOfLocalNight) * 0.9;
			}
			return results.stream()
				.map(object -> {
					Object[] objects = (Object[]) object;
					String meshCode = (String) objects[0];
					int numOfTarget = (int) objects[1];
					int numOfAll = (int) objects[2];
					int numOfLocalNight = (int) objects[3];
					double ratio;

					System.out.println(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight));
//					logger.debug(String.format("%s, %d, %d, %d", meshCode, numOfTarget, numOfAll, numOfLocalNight));
					
//					if (numOfAll > numOfLocalNight + 75) {
//						ratio = (double) (numOfTarget - numOfLocalNight) / (double) (numOfAll - numOfLocalNight);
//						ratio = ratio / ratioBase;
//						if (touristType.equals("nonlocal")) {
//							ratio = (1 - ratio) + 1;
//						}
//						return new HotAreaDTO(meshCode, ratio);
//					}
//					else {
//						return null;
//					}
					
					ratio = (double) (numOfTarget - numOfLocalNight) / (double) (numOfAll - numOfTarget);
					if (ratio >= 1.2 && ratio <= 5.0 && (numOfAll - numOfTarget) >= 30 && (numOfAll - numOfTarget) <= 280 && numOfTarget <= 1850) {
						ratio = 1.1 + (numOfTarget / 1850.0) * 0.7;
						return new HotAreaDTO(meshCode, ratio);
					}
					else if (ratio <= 0.0 && (numOfAll - numOfTarget) >= 30 && (numOfAll - numOfTarget) <= 60 && numOfTarget <= 1500) {
						ratio = 1.1 + (numOfTarget / 1500.0) * 0.7;
						return new HotAreaDTO(meshCode, ratio);
					}
					else if ((numOfAll - numOfTarget) >= 50) {
						ratio = 1.0 - ((numOfAll - numOfTarget) / 1000.0) * 0.8;
						if (ratio <= 0.2) {
							ratio = 0.2;
						}
						return new HotAreaDTO(meshCode, ratio);
					}
					else {
						return null;
					}
				})
				.filter(dto -> dto != null)
				.collect(Collectors.toList());
		}
		else {
			return null;
		}
	}
}
