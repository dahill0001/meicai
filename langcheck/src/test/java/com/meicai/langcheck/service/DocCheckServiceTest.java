package com.meicai.langcheck.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class DocCheckServiceTest {

	@InjectMocks
	private DocCheckService docCheckService;
	
	@Test
	public void testCheckFleschScore() {
		
		String pa = "Learn how to test a Spring boot web application. We will see some very quick examples (using Junit 5) and configurations for :";
		String pa2 = "How are you! Welcome to here";
		String pa3 = "Several bat species act as asymptomatic reservoirs for many viruses that are highly pathogenic in other mammals. Here, we have characterized the functional diversification of the protein kinase R (PKR), a major antiviral innate defense system. Our data indicate that PKR has evolved under positive selection and has undergone repeated genomic duplications in bats in contrast to all studied mammals that have a single copy of the gene. Functional testing of the relationship between PKR and poxvirus antagonists revealed how an evolutionary conflict with ancient pathogenic poxviruses has shaped a specific bat host-virus interface. We determined that duplicated PKRs of the Myotis species have undergone genetic diversification, allowing them to collectively escape from and enhance the control of DNA and RNA viruses. These findings suggest that viral-driven adaptations in PKR contribute to modern virus-bat interactions and may account for bat-specific immunity.";
		String pa4 = "Targeting necroptosis in NASH\r\n"
				+ "Impaired efferocytosis of dying cells is known to contribute to inflammation in nonalcoholic steatohepatitis (NASH). Shi et al. now show that impaired efferocytosis of necroptotic hepatocytes contributes to liver fibrosis in NASH. Targeting the CD47 “don’t eat me” signal on these necroptotic hepatocytes decreased the progression of hepatic fibrosis in a preclinical NASH model, and an anti-SIRPα antibody achieved similar results to anti-CD47, but without eliciting anemia in the mice. This paper implicates necroptotic hepatocytes in NASH fibrosis and suggests that this pathway may be targetable.—CAC\r\n"
				+ "Abstract\r\n"
				+ "Necroptosis contributes to hepatocyte death in nonalcoholic steatohepatitis (NASH), but the fate and roles of necroptotic hepatocytes (necHCs) in NASH remain unknown. We show here that the accumulation of necHCs in human and mouse NASH liver is associated with an up-regulation of the “don’t-eat-me” ligand CD47 on necHCs, but not on apoptotic hepatocytes, and an increase in the CD47 receptor SIRPα on liver macrophages, consistent with impaired macrophage-mediated clearance of necHCs. In vitro, necHC clearance by primary liver macrophages was enhanced by treatment with either anti-CD47 or anti-SIRPα. In a proof-of-concept mouse model of inducible hepatocyte necroptosis, anti-CD47 antibody treatment increased necHC uptake by liver macrophages and inhibited markers of hepatic stellate cell (HSC) activation, which is responsible for liver fibrogenesis. Treatment of two mouse models of diet-induced NASH with anti-CD47, anti-SIRPα, or AAV8-H1-shCD47 to silence CD47 in hepatocytes increased the uptake of necHC by liver macrophages and decreased markers of HSC activation and liver fibrosis. Anti-SIRPα treatment avoided the adverse effect of anemia found in anti-CD47–treated mice. These findings provide evidence that impaired clearance of necHCs by liver macrophages due to CD47-SIRPα up-regulation contributes to fibrotic NASH, and suggest therapeutic blockade of the CD47-SIRPα axis as a strategy to decrease the accumulation of necHCs in NASH liver and dampen the progression of hepatic fibrosis.";
		String score = docCheckService.checkFleschScore(pa, "test");
		String score2 = docCheckService.checkFleschScore(pa2, "test");
		String score3 = docCheckService.checkFleschScore(pa3, "test");
		String score4 = docCheckService.checkFleschScore(pa4, "test");
		System.out.println(score);
		System.out.println(score2);
		System.out.println(score3);
		System.out.println(score4);		
	}
	
	@Test
	public void testCheckWordFrequenciesScore() {
		
		String pa = "Learn how to test a Spring boot web application. We will see some very quick examples (using Junit 5) and configurations for :";
		String pa2 = "How are you! Welcome to here";
		String pa3 = "Several bat species act as asymptomatic reservoirs for many viruses that are highly pathogenic in other mammals. Here, we have characterized the functional diversification of the protein kinase R (PKR), a major antiviral innate defense system. Our data indicate that PKR has evolved under positive selection and has undergone repeated genomic duplications in bats in contrast to all studied mammals that have a single copy of the gene. Functional testing of the relationship between PKR and poxvirus antagonists revealed how an evolutionary conflict with ancient pathogenic poxviruses has shaped a specific bat host-virus interface. We determined that duplicated PKRs of the Myotis species have undergone genetic diversification, allowing them to collectively escape from and enhance the control of DNA and RNA viruses. These findings suggest that viral-driven adaptations in PKR contribute to modern virus-bat interactions and may account for bat-specific immunity.";
		String pa4 = "Targeting necroptosis in NASH\r\n"
				+ "Impaired efferocytosis of dying cells is known to contribute to inflammation in nonalcoholic steatohepatitis (NASH). Shi et al. now show that impaired efferocytosis of necroptotic hepatocytes contributes to liver fibrosis in NASH. Targeting the CD47 “don’t eat me” signal on these necroptotic hepatocytes decreased the progression of hepatic fibrosis in a preclinical NASH model, and an anti-SIRPα antibody achieved similar results to anti-CD47, but without eliciting anemia in the mice. This paper implicates necroptotic hepatocytes in NASH fibrosis and suggests that this pathway may be targetable.—CAC\r\n"
				+ "Abstract\r\n"
				+ "Necroptosis contributes to hepatocyte death in nonalcoholic steatohepatitis (NASH), but the fate and roles of necroptotic hepatocytes (necHCs) in NASH remain unknown. We show here that the accumulation of necHCs in human and mouse NASH liver is associated with an up-regulation of the “don’t-eat-me” ligand CD47 on necHCs, but not on apoptotic hepatocytes, and an increase in the CD47 receptor SIRPα on liver macrophages, consistent with impaired macrophage-mediated clearance of necHCs. In vitro, necHC clearance by primary liver macrophages was enhanced by treatment with either anti-CD47 or anti-SIRPα. In a proof-of-concept mouse model of inducible hepatocyte necroptosis, anti-CD47 antibody treatment increased necHC uptake by liver macrophages and inhibited markers of hepatic stellate cell (HSC) activation, which is responsible for liver fibrogenesis. Treatment of two mouse models of diet-induced NASH with anti-CD47, anti-SIRPα, or AAV8-H1-shCD47 to silence CD47 in hepatocytes increased the uptake of necHC by liver macrophages and decreased markers of HSC activation and liver fibrosis. Anti-SIRPα treatment avoided the adverse effect of anemia found in anti-CD47–treated mice. These findings provide evidence that impaired clearance of necHCs by liver macrophages due to CD47-SIRPα up-regulation contributes to fibrotic NASH, and suggest therapeutic blockade of the CD47-SIRPα axis as a strategy to decrease the accumulation of necHCs in NASH liver and dampen the progression of hepatic fibrosis.";
		String score = docCheckService.checkWordFrequenciesScore(pa, "test");
		String score2 = docCheckService.checkWordFrequenciesScore(pa2, "test");
		String score3 = docCheckService.checkWordFrequenciesScore(pa3, "test");
		String score4 = docCheckService.checkWordFrequenciesScore(pa4, "test");
		System.out.println(score);
		System.out.println(score2);
		System.out.println(score3);
		System.out.println(score4);		
	}

}
