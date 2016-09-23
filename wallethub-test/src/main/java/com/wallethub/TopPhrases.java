package com.wallethub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The problem calls attention for 'external sorting' which requests us to process a long
 * 	content which doesn't fit on a virtual memory thus it has to be done reading and wrinting to disk in chunks fashion.
 * 
 * The chunk approach by it self raises another need which is; we process (read content, figure hash out, count the number of occurences
 *  and write it on disk) so later when the whole content is processed and all of the chunks are done, we have to sort the
 *  resulting content (once the chunks might/probably have duplicates) and then take duplicates out (iterating/counting them properly before take them out).
 *  
 * Another requirement raised says to show top 100000 up, so at this point we can read the resulting content until the 100000th line and erase everything after this point.
 * This way the resulting content after all is entirely processed and we can check the top 100000 phrases out.
 * 
 * notes: It is not so easy to consider a whole efficient algorithm, but a set of efficient algorithms each one doing its job instead.
 * 	The solution here has been based on 'merge sort' recurring problem/algorithm but considering the limitation around virtual memory.
 * 
 * @author jean
 *
 */
public class TopPhrases {

	String workingDirectory = "/tmp/wallethub";

	public static void main(String[] args) {
		TopPhrases m = new TopPhrases();
		m.writeOnDisk();
		m.sortMerge( 0 );
	}
	
	private void writeOnDisk() {
		int[][] lines = new int[][]{
//			new int[]{ 1, 6, -4, 9, -10, 5, -6, -7, 50, 51, 2, -97 },
//			new int[]{ 6, 4, 9, 8, 2, 4, 9, 7, 3, 7, 4, 6, 1 },
//			new int[]{ 1, 1, 1, 2, 2, 2, 3, 3, 9, -4, -1, -10, -50 }
			new int[]{ 108, 15, 50, 4, 8, 42, 23, 16, 17 }
		};
		int execution = 0;
		int pageNumber = 0;
		FileOutputStream fos = null;

		for ( int[] line : lines ) {
			for ( int _int : line ) {
				try {
					File pageFile = new File( this.workingDirectory, execution + "_" + ( ++pageNumber ) + "_.page" );
					if ( pageFile.exists() || !pageFile.createNewFile() ) {
						throw new IllegalStateException( "Impossible create file: " + pageFile.getAbsolutePath() );
					}
					
					fos = new FileOutputStream( pageFile );
					fos.write( String.valueOf( _int ).getBytes() );
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if ( fos != null ) {
						try {
							fos.close();
							fos = null;
						} catch (IOException e) { }
					}
				}
			}
		}
	}
	
	private void sortMerge( final int execution ) {
		File workingDirectory = new File( this.workingDirectory );
		Pattern pattern = Pattern.compile( "^[" + execution + "]_(\\d+)_\\.page" );
		
		List< String > fileList = Arrays.asList( workingDirectory.list( new FilenameFilter() {
			@Override
			public boolean accept( File dir, String name ) {
				return pattern.matcher( name ).matches();
			}
		}));
		
		fileList.sort( new Comparator<String>() {
			@Override
			public int compare( String fileNameOne, String fileNameTwo ) {
				Matcher matchFileOne = pattern.matcher( fileNameOne );
				matchFileOne.matches();
				
				Matcher matchFileTwo = pattern.matcher( fileNameTwo );
				matchFileTwo.matches();
				
				return new Integer( matchFileOne.group( 1 ) ).compareTo( new Integer( matchFileTwo.group( 1 ) ) ) ;
			}
		});
		
		int pageNumber = 0;
		File pageFileLeft, pageFileRight = null;
		for ( int i = 0; i < fileList.size(); i++ ) {
			pageFileLeft = new File( workingDirectory, fileList.get( i ) );
			if ( ++i < fileList.size() ) {
				pageFileRight = new File( workingDirectory, fileList.get( i ) );
			}
			
			this.sortMerge( ( execution + 1 ), ( ++pageNumber ) , pageFileLeft, pageFileRight );
		}
		
		if ( fileList.size() > 2 ) this.sortMerge( execution + 1 );
	}
	
	private void sortMerge( int nextExecution, int pageNumber, File fileLeft, File fileRight ) {
		BufferedReader fisLeft = null, fisRight = null;
		PrintWriter pw = null;
		try {
			File pageFile = new File( this.workingDirectory, nextExecution + "_" + pageNumber + "_.page" );
			if ( pageFile.exists() || !pageFile.createNewFile() ) {
				throw new IllegalStateException( "Impossible create file: " + pageFile.getAbsolutePath() );
			}
			pw = new PrintWriter( new FileOutputStream( pageFile ) );
			
			fisLeft = fileLeft == null ? null : new BufferedReader( new InputStreamReader( new FileInputStream( fileLeft ) ) );
			fisRight = fileRight == null ? null : new BufferedReader( new InputStreamReader( new FileInputStream( fileRight ) ) );
			
			String valueLeft = null, valueRight = null;
			valueLeft = fisLeft == null ? null : fisLeft.readLine();
			valueRight = fisRight == null ? null : fisRight.readLine();
			
			while ( valueLeft != null || valueRight != null ) {
				if ( valueLeft == null ) {
					pw.println( valueRight );
					valueRight = fisRight.readLine();
				} else if ( valueRight == null ) {
					pw.println( valueLeft );
					valueLeft = fisLeft.readLine();
				} else {
					if ( new Integer( valueLeft ).compareTo( new Integer( valueRight ) ) <= 0 ) {
						pw.println( valueLeft );
						valueLeft = fisLeft.readLine();
					} else {
						pw.println( valueRight );
						valueRight = fisRight.readLine();
					}
				}
			}
			pw.flush();
		} catch ( FileNotFoundException e ) {
			new RuntimeException( e );
		} catch (IOException e) {
			new RuntimeException( e );
		} finally {
			if ( fisLeft != null ) {
				try {
					fisLeft.close();
				} catch ( IOException e ) { }
			}
			if ( fisRight != null ) {
				try {
					fisRight.close();
				} catch (IOException e) { }
			}
			if ( pw != null ) {
				pw.close();
			}
		}
	}
}
