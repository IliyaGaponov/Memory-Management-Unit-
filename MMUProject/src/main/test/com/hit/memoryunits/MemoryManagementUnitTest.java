package com.hit.memoryunits;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;

public class MemoryManagementUnitTest
{
	private final int sizeOfRam = 5;

	@Test
	public void testMMU() throws IOException
	{
		IAlgoCache<Long, Long> algo = new LRUAlgoCacheImpl<Long, Long>(sizeOfRam);
		MemoryManagementUnit mmu = new MemoryManagementUnit(sizeOfRam, algo);
		
		Long[] pagesId = {1L, 2L, 3L, 4L, 5L, 6L};
		assertEquals(0, mmu.getRam().getPages().size()); //ram is empty
		Page<byte[]>[] pagesFromHD = mmu.getPages(pagesId); // pages id in Ram: 2,3,4,5,6
		
		assertNull(mmu.getRam().getPage(pagesId[0])); // there is no page in RAM with id 1
		assertEquals(Long.valueOf(2), mmu.getRam().getPage(pagesId[1]).getPageId());
		assertEquals(Long.valueOf(3), mmu.getRam().getPage(pagesId[2]).getPageId());
		assertEquals(Long.valueOf(4), mmu.getRam().getPage(pagesId[3]).getPageId());
		assertEquals(Long.valueOf(5), mmu.getRam().getPage(pagesId[4]).getPageId());
		assertEquals(Long.valueOf(6), mmu.getRam().getPage(pagesId[5]).getPageId());

		Long[] pages = {30L,40L};
		pagesFromHD = mmu.getPages(pages);
		assertEquals(pagesFromHD[0], pagesFromHD[0]);


		mmu.shutDown();		
		assertEquals(0,  mmu.getRam().getPages().size()); // the RAM is empty after shutDown
		assertEquals(1000, HardDisk.getInstance().getHardDiskMap().size()); //the HD size is 1000  
	}
}
